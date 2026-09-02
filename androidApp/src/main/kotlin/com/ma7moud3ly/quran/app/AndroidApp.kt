package com.ma7moud3ly.quran.app

import android.app.Application
import com.ma7moud3ly.quran.di.AppModule
import org.koin.plugin.module.dsl.modules
import com.ma7moud3ly.quran.platform.AndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level


class AndroidApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidApp.init(this)
        startKoin {
            androidLogger(Level.DEBUG) // Or Level.INFO, Level.ERROR
            androidContext(this@AndroidApp)
            modules(AppModule::class)
        }
    }
}
