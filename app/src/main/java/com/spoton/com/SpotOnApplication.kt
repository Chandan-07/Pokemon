package com.spoton.com

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CryptoApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}