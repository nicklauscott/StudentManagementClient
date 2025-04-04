package org.example.project.presentation.screens.index

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.presentation.screens.home.HomeScreen
import org.example.project.presentation.theme.LocalAppTheme

@Composable
fun IndexScreen() {
    val appTheme = LocalAppTheme.current
    Row(
        modifier = Modifier.fillMaxSize()
            .background(appTheme.colors.mainBackGround5)
    ) {
        // side panel
        Surface(
            modifier = Modifier.fillMaxHeight()
                .weight(0.18f),
                elevation = 4.dp,
                color = appTheme.colors.sidePanelBackGround
        ) {  }

        // main page
        Column(modifier = Modifier.fillMaxHeight().weight(0.88f)) {
            HomeScreen()
        }
    }
}