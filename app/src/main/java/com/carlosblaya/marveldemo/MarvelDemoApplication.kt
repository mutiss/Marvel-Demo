package com.carlosblaya.marveldemo

import android.app.Application
import android.content.Context
import com.carlosblaya.marveldemo.di.*
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@HiltAndroidApp
class MarvelDemoApplication: Application(){

    override fun onCreate() {
        super.onCreate()
    }

}