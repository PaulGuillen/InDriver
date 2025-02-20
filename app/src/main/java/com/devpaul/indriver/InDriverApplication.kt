package com.devpaul.indriver

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class InDriverApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}