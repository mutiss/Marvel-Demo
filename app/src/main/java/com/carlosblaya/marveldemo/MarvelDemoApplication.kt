package com.carlosblaya.marveldemo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MarvelDemoApplication: Application(){

    override fun onCreate() {
        super.onCreate()
    }

}