package org.example.project.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import org.example.project.presentation.theme.LocalAppTheme

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
fun CustomButton(text: String, enabled: Boolean, saving: Boolean, onClick: () -> Unit) {
    val appTheme = LocalAppTheme.current
    Button(
        onClick = onClick,
        modifier = Modifier
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
