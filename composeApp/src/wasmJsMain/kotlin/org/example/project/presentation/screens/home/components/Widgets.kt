package org.example.project.presentation.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import org.example.project.presentation.theme.LocalAppTheme
import org.jetbrains.compose.resources.painterResource
import studentmanagementclient.composeapp.generated.resources.Res
import studentmanagementclient.composeapp.generated.resources.user_icon

@Composable
fun NoActiveStudent() {
    val appTheme = LocalAppTheme.current
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painterResource(Res.drawable.user_icon), null,
            modifier = Modifier.size(200.dp),
            colorFilter = ColorFilter.tint(appTheme.colors.onBackGround2)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Select a student to view their detail",
            style = MaterialTheme.typography.h6,
            color = appTheme.colors.onBackGround2
        )
    }
}


