package com.ma7moud3ly.quran

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.ma7moud3ly.quran.di.AppModule
import org.koin.plugin.module.dsl.modules
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.core.context.GlobalContext.startKoin
import com.ma7moud3ly.quran.resources.Res
import com.ma7moud3ly.quran.resources.app_name
import com.ma7moud3ly.quran.resources.logo

fun main() = application {
    startKoin {
        modules(AppModule::class)
    }
    Window(
        onCloseRequest = ::exitApplication,
        title = stringResource(Res.string.app_name),
        icon = painterResource(Res.drawable.logo),
        state = rememberWindowState(
            position = WindowPosition.Aligned(Alignment.Center),
            size = DpSize(750.dp, 750.dp),
        )
    ) {
        App()
    }
}