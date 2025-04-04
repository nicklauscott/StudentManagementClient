package org.example.project.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import org.example.project.presentation.theme.LocalAppTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun FooterItem(
    modifier: Modifier = Modifier,
    text: String, icon: DrawableResource,
    backgroundColor: Color = Color.DarkGray,
    active: Boolean, onClick: () -> Unit
) {
    val appTheme = LocalAppTheme.current
    Surface(
        modifier = modifier
            .padding(horizontal = 4.dp)
            .fillMaxWidth(),
        elevation = 4.dp,
        color = backgroundColor.copy(if (!active) 1f else 0.7f),
        shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
    ) {
        Row(
            modifier = modifier.fillMaxWidth()
                .clickable(enabled = !active) { onClick() }
                .padding(5.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painterResource(icon), null,
                modifier = Modifier.size(25.dp),
                colorFilter = ColorFilter.tint(appTheme.colors.onBackGround2.copy(
                    if (!active) 1f else 0.8f
                ))
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.body1,
                color = if (!active) appTheme.colors.onBackGround4
                else appTheme.colors.onBackGround2
            )
        }
    }
}