package com.ma7moud3ly.quran

import com.ma7moud3ly.quran.di.AppModule
import org.koin.plugin.module.dsl.modules
import org.koin.core.context.startKoin

fun initKoinForIOS() {
    startKoin {
        printLogger()
        modules(AppModule::class)
    }
}