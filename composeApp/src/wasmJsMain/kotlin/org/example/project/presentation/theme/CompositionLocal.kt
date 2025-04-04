package org.example.project.presentation.theme

import androidx.compose.runtime.compositionLocalOf

data class AppTheme(
    val colors: AppColors = AppColors()
)

val LocalAppTheme = compositionLocalOf { AppTheme() }