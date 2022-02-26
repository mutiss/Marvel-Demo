package com.carlosblaya.marveldemo.di

import com.carlosblaya.marveldemo.data.network.services.CharacterApiInterface
import com.carlosblaya.marveldemo.data.repository.CharacterListRepositoryImpl
import com.carlosblaya.marveldemo.data.response.mapper.CharacterListMapper
import com.carlosblaya.marveldemo.domain.repository.CharacterListRepository
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
    fun provideCharacterRepository(api: CharacterApiInterface, characterListMapper: CharacterListMapper): CharacterListRepository = CharacterListRepositoryImpl(api,characterListMapper)

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
