package com.carlosblaya.marveldemo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttp(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.addInterceptor(interceptor)
        okHttpClientBuilder.connectTimeout(60L, TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(60L, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(60L, TimeUnit.SECONDS)
        return okHttpClientBuilder.build()
    }
}