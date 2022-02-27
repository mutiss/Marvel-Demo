package com.carlosblaya.marveldemo.di

import com.carlosblaya.marveldemo.data.database.dao.CharacterDao
import com.carlosblaya.marveldemo.data.network.services.CharacterApiInterface
import com.carlosblaya.marveldemo.data.repository.CharacterRepositoryImpl
import com.carlosblaya.marveldemo.data.response.mapper.CharacterMapper
import com.carlosblaya.marveldemo.domain.repository.CharacterRepository
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
class CharacterModule {

    @Provides
    @Singleton
    fun provideCharacterRepository(api: CharacterApiInterface, characterListMapper: CharacterMapper, characterDao: CharacterDao): CharacterRepository = CharacterRepositoryImpl(api,characterListMapper,characterDao)

    @Provides
    fun provideCharacterApi(okHttpClient: OkHttpClient): CharacterApiInterface {
        return Retrofit.Builder()
            .baseUrl(Konsts.BASE_URL_SERVICE)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(CharacterApiInterface::class.java)
    }
}
