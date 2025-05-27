package com.example.jitbook.book

import android.app.Application
import com.example.jitbook.di.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin

class BookApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BookApplication)
            modules(sharedModule)

        }
    }
}