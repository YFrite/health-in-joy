package com.yfrite.healthinjoy

import android.app.Application
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp

//test
@HiltAndroidApp
class AppApplication: Application(), Configuration.Provider {
    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .build()
}