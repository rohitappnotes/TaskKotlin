package com.task.data.remote.server.cache

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.TimeUnit

/*
 * This interceptor will be called both if the network is available and if the network is not available
 *
 * If there is no Internet, get the cache that was stored 7 days ago. If the cache is older than 7 days,
 * then discard it, and indicate an error in fetching the response.
 * The 'max-stale' attribute is responsible for this behavior.
 * The 'only-if-cached' attribute indicates to not retrieve new data; fetch the cache only instead.
 */
class ProvideOfflineCacheInterceptor(private var mContext: Context) : Interceptor {

    private val mTag: String = ProvideOfflineCacheInterceptor::class.java.simpleName

    companion object {
        /**
         * Default, Expired in once week.
         * read from cache if there is no internet connection, 7 days old
         */
        var DEFAULT_CACHE_DURATION_WITHOUT_NETWORK_IN_SECONDS = 7 * 24 * 60 * 60
        /**
         * max-stale is the highest limit beyond which cache cannot be returned. It also takes value
         * in seconds.
         */
        val MAX_STALE = DEFAULT_CACHE_DURATION_WITHOUT_NETWORK_IN_SECONDS
        const val HEADER_CACHE_CONTROL = "Cache-Control"
        const val HEADER_PRAGMA = "Pragma"
    }

    constructor(context: Context, cacheDurationWithoutNetworkInSeconds: Int) : this(context) {
        DEFAULT_CACHE_DURATION_WITHOUT_NETWORK_IN_SECONDS = cacheDurationWithoutNetworkInSeconds
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (!isNetworkAvailable(mContext)) {
            val cacheControl = CacheControl.Builder()
                .maxStale(MAX_STALE, TimeUnit.DAYS)
                .build()

            request = request.newBuilder()
                .removeHeader(HEADER_PRAGMA)
                .removeHeader(HEADER_CACHE_CONTROL)
                .cacheControl(cacheControl)
                .build()
        }

        return chain.proceed(request)
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        var result = false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            result = when {
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION")
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }
                }
            }
        }
        return result
    }
}