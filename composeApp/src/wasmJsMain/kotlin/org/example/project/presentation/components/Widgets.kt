package org.example.project.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.example.project.presentation.theme.LocalAppTheme


fun Modifier.enableClick(enabled: Boolean, onClick: () -> Unit): Modifier {
    return if (enabled) this
        .then(clip(RoundedCornerShape(4.dp)))
        .then(clickable(true) { onClick() })
        .padding(2.dp)
    else this
}

@Composable
fun InfoCell(
    text: String, style: TextStyle = MaterialTheme.typography.body2, color: Color = Color.White,
    verticalPadding: Dp = 0.8.dp, horizontalPadding: Dp = 0.dp,
    clickable: Boolean = false, modifier: Modifier = Modifier,  onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .padding(vertical = verticalPadding, horizontal = horizontalPadding)
            .enableClick(enabled = clickable, onClick),
        verticalAlignment = Alignment.CenterVertically
    ) { Text(text = text, style = style, color = color) }
}

@Composable
fun HeaderText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.h4,
        color = LocalAppTheme.current.colors.onBackGround6
    )
}

@Composable
fun ButtonIcon(imageVector: ImageVector, saving: Boolean, onClick: () -> Unit) {
    val appTheme = LocalAppTheme.current
    IconButton(
        onClick = onClick,
        enabled = !saving,
        modifier = Modifier
            .padding(3.dp)
    ) {
        if (!saving) Icon(imageVector = imageVector, contentDescription = null, tint = appTheme.colors.onBackGround2)
        else CircularProgressIndicator(color = appTheme.colors.onBackGround2)
    }
}

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String, enabled: Boolean, saving: Boolean, onClick: () -> Unit
) {
    val appTheme = LocalAppTheme.current
    Button(
        onClick = onClick,
        modifier = modifier
            .padding(5.dp),
        enabled = enabled && !saving,
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = appTheme.colors.button,
            disabledBackgroundColor = appTheme.colors.button.copy(0.5f)
        )
    ) {
        if (!saving) {
            Text(text, style = MaterialTheme.typography.button, color = appTheme.colors.buttonText.copy(if (enabled) 1f else 0.7f))
        }
        else CircularProgressIndicator(color = appTheme.colors.onBackGround2, modifier = Modifier.size(15.dp), strokeWidth = 2.dp)
    }
}
