package com.task.data.remote.server.cache

import android.content.Context
import com.task.data.remote.server.ApiConfiguration
import okhttp3.Cache
import java.io.File

/**
 * What is Caching?
 *
 * Caching is nothing but a way to store network fetched data on a device’s storage and access later
 * when the device is offline or we want the same data again and again. Some image loading library
 * like Picasso or Glide provide this caching when loading and displaying images but retrofit does
 * not use this by default for its requests
 *
 * If we use the Retrofit without modifying the default behaviour it does not cache the data. Because
 * Retrofit uses default OkHttp client for network request and default OkHttp behaviour does not cache
 * the data but Okhttp has components called interceptor. So to do this we will apply interceptor in
 * the OkHttp instance to serve the purpose of cache and then we will apply this created OkHttp instance
 * as the Retrofit client which cache the and provide this cached data when there is no internet connection
 * available.
 *
 * Benefits of Caching ?
 *
 * Reduces bandwidth consumption.
 *
 * Saves you time you would spend waiting for the server to give you the network response.
 *
 * Saves the server the burden of additional traffic.
 *
 * If you need to access the same network resource again after having accessed it recently, your
 * device won’t need to make a request to the server; it’ll get the cached response instead.
 *
 * Always remember ?
 *
 * Make sure you are using a GET request and not a POST!
 *
 * Always make sure you add .removeHeader("Pragma").
 *
 * Avoid using the HttpLoggingInterceptor while testing, it can cause some confusion in the beginning.
 * Enable it in the end if you want. ALWAYS ALWAYS ALWAYS delete your app from the device and reinstall
 * it again upon every change in code, if you want to explore using Interceptors. Otherwise changing
 * code while the old cache data is still on the device will cause you lots of confusion and misleading
 * deductions!
 *
 * The order of adding Interceptors to OKHTTPClient object matters!
 */
class CacheConfig(private var mContext: Context) {

    private val mTag: String = CacheConfig::class.java.simpleName

    private var mCacheDirectoryName: String = ApiConfiguration.DEFAULT_OK_HTTP_CACHE_DIR_NAME
    /**
     * Note: Cache size must be a Long
     */
    private var mCacheSize: Long = ApiConfiguration.DEFAULT_OK_HTTP_CACHE_SIZE

    private lateinit var cache: Cache
    private lateinit var cacheDirectory: File

    constructor(context: Context, cacheDirectoryName: String, cacheSize: Long) : this(context) {
        mCacheDirectoryName = cacheDirectoryName
        mCacheSize = cacheSize
    }

    constructor(context: Context, cacheDirectoryName: String) : this(context) {
        mCacheDirectoryName = cacheDirectoryName
    }

    fun getCache(): Cache {
        cacheDirectory = File(mContext.cacheDir, mCacheDirectoryName)
        cache = Cache(cacheDirectory, mCacheSize)
        return cache
    }
}