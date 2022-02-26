package com.carlosblaya.marveldemo.di

import android.content.Context
import com.carlosblaya.marveldemo.data.response.mapper.CharacterListMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Provides
    @Singleton
    fun provideCharacterListMapper(): CharacterListMapper = CharacterListMapper()

}
