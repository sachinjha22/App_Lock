package com.karmakarin.safebox.util

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class OCApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}