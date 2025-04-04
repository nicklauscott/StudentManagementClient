package org.example.project

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import org.example.project.di.module
import org.example.project.domain.alert
import org.example.project.presentation.screens.home.HomeScreen
import org.example.project.presentation.screens.index.IndexScreen
import org.example.project.presentation.theme.AppTheme
import org.example.project.presentation.theme.LocalAppTheme
import org.koin.core.context.startKoin

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    startKoin { modules(module) }
    val appTheme = AppTheme()
    ComposeViewport(document.body!!) {
        CompositionLocalProvider(LocalAppTheme provides appTheme) {
            IndexScreen()
        }
    }
}


