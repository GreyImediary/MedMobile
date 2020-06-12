package com.example.medmobile.ui

import android.app.Application
import com.example.medmobile.di.apiModule
import com.example.medmobile.di.baseApiModule
import com.example.medmobile.di.loginModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MedMobile : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MedMobile)
            modules(baseApiModule, apiModule, loginModule)
        }
    }
}