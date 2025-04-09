package org.example.project.presentation.screens.index

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import org.example.project.presentation.screens.home.HomeScreen
import org.example.project.presentation.screens.login.SignInAndSignUpScreen
import org.example.project.presentation.screens.sidepanel.SidePanelScreen
import org.example.project.presentation.theme.LocalAppTheme
import org.jetbrains.compose.resources.painterResource
import org.koin.core.context.GlobalContext
import studentmanagementclient.composeapp.generated.resources.Res
import studentmanagementclient.composeapp.generated.resources.app_icon

@Composable
fun IndexScreen() {
    val appTheme = LocalAppTheme.current
    val manager = remember { GlobalContext.get().get<IndexScreenManager>() }
    val screenState = remember { mutableStateOf<Boolean?>(null) }
    val scale = remember { Animatable(0f) }

    LaunchedEffect(true) {
        scale.animateTo(1f, animationSpec = tween(durationMillis = 1500, easing = EaseInBounce ))
        screenState.value = manager.isTokenValid.value
    }

    Box(
        modifier = Modifier.fillMaxSize()
        .background(appTheme.colors.mainBackGround5),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(screenState) { targetState ->
            when (targetState.value) {
                true -> {
                    Row(modifier = Modifier.fillMaxSize()) {
                        // side panel
                        Surface(
                            modifier = Modifier.fillMaxHeight()
                                .weight(0.18f),
                            elevation = 4.dp,
                            color = appTheme.colors.sidePanelBackGround
                        ) {
                            SidePanelScreen()
                        }

                        // main page
                        Column(modifier = Modifier.fillMaxHeight().weight(0.88f)) {
                            HomeScreen()
                        }
                    }
                }
                false -> SignInAndSignUpScreen()
                else -> {
                    Image(
                        painterResource(Res.drawable.app_icon), null,
                        modifier = Modifier.scale(scale.value),
                        colorFilter = ColorFilter.tint(appTheme.colors.onBackGround2)
                    )
                }
            }
        }
    }
}

