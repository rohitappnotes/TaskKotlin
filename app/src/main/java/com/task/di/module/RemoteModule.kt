package com.task.di.module

import android.content.Context
import android.util.Log
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.task.data.remote.server.ApiConfiguration
import com.task.data.remote.server.ApiService
import com.task.data.remote.server.cache.CacheConfig
import com.task.data.remote.server.cache.ProvideCacheInterceptor
import com.task.data.remote.server.cache.ProvideOfflineCacheInterceptor
import com.task.di.qualifier.isDebug
import com.task.di.qualifier.remote.ApiKey
import com.task.di.qualifier.remote.BaseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {

    @BaseUrl
    @Provides
    @Singleton
    fun provideBaseUrl(): String {
        return ApiConfiguration.BASE_URL
    }

    @ApiKey
    @Provides
    @Singleton
    fun provideApiKey(): String {
        return ApiConfiguration.API_KEY
    }

    /**
     * Okhttp is the default HttpClient for Retrofit.
     *
     * Provides the instance of OkHttpClient
     * @return OkHttpClient
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        cache: Cache,
        offlineCacheInterceptor: ProvideOfflineCacheInterceptor,
        cacheInterceptor: ProvideCacheInterceptor,
        @isDebug isDebug: Boolean,
        loggingInterceptor: LoggingInterceptor
    ): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()

        okHttpClientBuilder.connectTimeout(
            ApiConfiguration.CUSTOM_HTTP_CONNECT_TIMEOUT_IN_SECONDS.toLong(),
            TimeUnit.SECONDS
        )
        okHttpClientBuilder.writeTimeout(
            ApiConfiguration.CUSTOM_HTTP_WRITE_TIMEOUT_IN_SECONDS.toLong(),
            TimeUnit.SECONDS
        )
        okHttpClientBuilder.readTimeout(
            ApiConfiguration.CUSTOM_HTTP_READ_TIMEOUT_IN_SECONDS.toLong(),
            TimeUnit.SECONDS
        )

        okHttpClientBuilder.cache(cache)

        okHttpClientBuilder.addInterceptor(offlineCacheInterceptor)
        okHttpClientBuilder.addNetworkInterceptor(cacheInterceptor)

        if (isDebug) {
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }

        return okHttpClientBuilder.build()
    }

    /**
     * Provides the instance of retrofit
     * @param okHttpClient
     * @return Retrofit
     */
    @Provides
    @Singleton
    fun provideRetrofit(@BaseUrl baseUrl: String, gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        val builder = Retrofit.Builder()
        builder.baseUrl(baseUrl)
        builder.client(okHttpClient)
        builder.addConverterFactory(GsonConverterFactory.create(gson))
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        return builder.build()
    }

    /**
     * @param retrofit
     * @return created implementation ApiService
     */
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCache(@ApplicationContext context: Context): Cache {
        val cacheConfig = CacheConfig(context)
        //val cacheConfig = CacheConfig(context, ApiConfiguration.CUSTOM_OK_HTTP_CACHE_DIR_NAME)
        //val cacheConfig = CacheConfig(context, ApiConfiguration.CUSTOM_OK_HTTP_CACHE_DIR_NAME, ApiConfiguration.CUSTOM_OK_HTTP_CACHE_SIZE)
        return cacheConfig.getCache()
    }

    @Provides
    @Singleton
    fun provideOfflineCacheInterceptor(@ApplicationContext context: Context): ProvideOfflineCacheInterceptor {
        /*return ProvideOfflineCacheInterceptor(
            context,
            ApiConfiguration.CUSTOM_CACHE_DURATION_WITHOUT_NETWORK_IN_SECONDS
        )*/
        return ProvideOfflineCacheInterceptor(context)
    }

    @Provides
    @Singleton
    fun provideCacheInterceptor(@ApplicationContext context: Context): ProvideCacheInterceptor {
        /* return ProvideCacheInterceptor(
             context,
             ApiConfiguration.CUSTOM_CACHE_DURATION_WITH_NETWORK_IN_SECONDS
         )*/
        return ProvideCacheInterceptor(context)
    }

    @Provides
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setLenient()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): LoggingInterceptor {
        return LoggingInterceptor.Builder().setLevel(Level.BASIC).log(Log.VERBOSE).build()
    }













    /*
     @Provides
    @Singleton
    fun providesRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val logging = HttpLoggingInterceptor(CustomHttpLogger())
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BASIC)
        }

    @Provides
    @Singleton
    fun providesGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

     */
}

