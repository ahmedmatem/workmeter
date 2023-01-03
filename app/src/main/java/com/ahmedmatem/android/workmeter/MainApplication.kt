package com.ahmedmatem.android.workmeter

import android.app.Application
import com.ahmedmatem.android.workmeter.di.koin.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // use Android logger - Level.INFO by default
            androidLogger()
            // Reference Android context
            androidContext(this@MainApplication)
            // Load modules
            modules(appModule)
        }
    }
}