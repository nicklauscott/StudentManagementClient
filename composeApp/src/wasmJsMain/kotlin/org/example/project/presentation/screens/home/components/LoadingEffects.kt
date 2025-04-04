package org.example.project.presentation.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.example.project.presentation.components.ShimmerEffect
import org.example.project.presentation.theme.LocalAppTheme

@Composable
fun LoadingStudentCell(modifier: Modifier = Modifier) {
    val appTheme = LocalAppTheme.current
    Row(
        modifier = modifier.fillMaxWidth().padding(vertical = 4.dp, horizontal = 0.5.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(appTheme.colors.mainBackGround3)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        // picture
        Surface(
            modifier = Modifier.size(60.dp),
            shape = CircleShape,
            color = appTheme.colors.mainBackGround2
        ) {
            ShimmerEffect(modifier.size(100.dp))
        }

        Spacer(modifier = Modifier.width(18.dp))

        Column(
            modifier = Modifier.padding(vertical = 1.dp)
        ) {
            repeat(3) {
                LoadingInfoCell()
            }
        }
    }
}

@Composable
fun LoadingInfoCell() {
    val width = remember { mutableStateOf((200..260).random().dp) }
    val appTheme = LocalAppTheme.current
    Box(
        modifier = Modifier
            .padding(vertical = 2.dp)
            .height(13.dp).width(width.value)
            .clip(RoundedCornerShape(64.dp))
            .background(appTheme.colors.mainBackGround2.copy(alpha = 0.5f))
    ) {
        ShimmerEffect()
    }
}