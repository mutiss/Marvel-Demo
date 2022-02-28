package com.carlosblaya.marveldemo.di

import com.carlosblaya.marveldemo.data.response.mapper.CharacterMapper
import com.carlosblaya.marveldemo.data.response.mapper.ComicMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Provides
    @Singleton
    fun provideCharacterMapper(): CharacterMapper = CharacterMapper()

    @Provides
    @Singleton
    fun provideComicMapper(): ComicMapper = ComicMapper()
}
