package com.task.data.remote.server.cache

import android.content.Context
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit
import java.io.IOException

/**
 * This interceptor will be called ONLY if the network is available
 *
 * If there is Internet, get the cache that was stored 5 seconds ago.
 * If the cache is older than 5 seconds, then discard it, and indicate an error in fetching the response.
 * The 'max-age' attribute is responsible for this behavior.
 */
class ProvideCacheInterceptor(private var mContext: Context) : Interceptor {

    private val mTag: String = ProvideCacheInterceptor::class.java.simpleName

    companion object {
        /**
         * Default, Expired in 5 Second
         */
        var DEFAULT_CACHE_DURATION_WITH_NETWORK_IN_SECONDS = 5
        /**
         * max-age is the oldest limit ( lower limit) till which the response can be returned from
         * the cache. It takes value in seconds.
         */
        val MAX_AGE = DEFAULT_CACHE_DURATION_WITH_NETWORK_IN_SECONDS
        const val HEADER_CACHE_CONTROL = "Cache-Control"
        const val HEADER_PRAGMA = "Pragma"
    }

    constructor(context: Context, cacheDurationWithNetworkInSeconds: Int) : this(context) {
        DEFAULT_CACHE_DURATION_WITH_NETWORK_IN_SECONDS = cacheDurationWithNetworkInSeconds
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val cacheControl = CacheControl.Builder()
            .maxAge(MAX_AGE, TimeUnit.SECONDS)
            .build()
        return response.newBuilder()
            .removeHeader(HEADER_PRAGMA)
            .removeHeader(HEADER_CACHE_CONTROL)
            .header(HEADER_CACHE_CONTROL, cacheControl.toString())
            .build()
    }
}