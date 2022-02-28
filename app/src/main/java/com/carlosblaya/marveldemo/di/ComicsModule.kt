package com.carlosblaya.marveldemo.di

import com.carlosblaya.marveldemo.data.network.services.ComicApiInterface
import com.carlosblaya.marveldemo.data.repository.ComicRepositoryImpl
import com.carlosblaya.marveldemo.data.response.mapper.ComicMapper
import com.carlosblaya.marveldemo.domain.repository.ComicRepository
import com.carlosblaya.marveldemo.util.Konsts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
class ComicsModule {

    @Provides
    @Singleton
    fun provideComicRepository(api: ComicApiInterface, comicListMaper: ComicMapper): ComicRepository = ComicRepositoryImpl(api, comicListMaper)

    @Provides
    fun provideComicApi(okHttpClient: OkHttpClient): ComicApiInterface {
        return Retrofit.Builder()
            .baseUrl(Konsts.BASE_URL_SERVICE)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ComicApiInterface::class.java)
    }
}