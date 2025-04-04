package org.example.project.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.presentation.screens.home.components.InfoCell
import org.example.project.presentation.theme.LocalAppTheme

@Composable
fun EnhacedTextAndTextFieldCell(
    modifier: Modifier,
    id: Int, label: String, textState: String, canEdit: Boolean,
    singleLine: Boolean, onTextChange: (String) -> Unit = {},
    errorAndSuccess: () -> Pair<Boolean, Boolean>
) {
    val appTheme = LocalAppTheme.current
    Column(
        modifier = modifier.padding(10.dp).padding(top = 5.dp),
        horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Center
    ) {
        InfoCell(text = label, style = MaterialTheme.typography.subtitle2, color = appTheme.colors.textOnMainBg)
        Spacer(modifier = Modifier.height(2.dp))
        EnhancedTextField(
            id = id, label = label, textState = textState, canEdit = canEdit, singleLine = singleLine,
            onTextChange = onTextChange, errorAndSuccess = errorAndSuccess
        )
    }
}

@Composable
fun EnhancedTextField(
    id: Int, label: String, textState: String, canEdit: Boolean = true,
    singleLine: Boolean, onTextChange: (String) -> Unit, errorAndSuccess: () -> Pair<Boolean, Boolean>
) {
    val appTheme = LocalAppTheme.current
    var isFocused by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }  // Set this based on validation logic
    var isSuccess by remember { mutableStateOf(false) }  // Set this based on validation logic

    LaunchedEffect(id) {
        isFocused = false
        isError = false
        isSuccess = false
    }

    if (!canEdit) { isFocused = false }

    // Dynamic border color based on state (error, success, focus)
    val borderColor by animateColorAsState(
        targetValue = when {
            isError -> appTheme.colors.error
            isSuccess -> appTheme.colors.success
            isFocused -> appTheme.colors.textFieldBorder
            else -> appTheme.colors.borderStroke
        }
    )

    // Dynamic text color
    val textColor by animateColorAsState(
        targetValue = if (isError) appTheme.colors.error else appTheme.colors.textOnMainBg
    )

    val background by animateColorAsState(
        targetValue = if (canEdit) {
            if (isFocused) appTheme.colors.sidePanelBackGround else appTheme.colors.mainBackGround4
        } else appTheme.colors.mainBackGround5
    )

    // Wrapping container with padding, shadow, and background gradient
    Box(
        modifier = Modifier.fillMaxWidth(),
        // .padding(16.dp)
        //  .zIndex(1f), // Ensures it's above other content
        contentAlignment = Alignment.TopStart
    ) {
        // Card container for the capsule TextField
        Card(
            shape = RoundedCornerShape(6.dp),
            elevation = 12.dp,
            backgroundColor = background,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Capsule-shaped BasicTextField with enhancements
            BasicTextField(
                value = textState,
                onValueChange = {
                    if (canEdit) {
                        onTextChange(it)
                        isError = errorAndSuccess().first
                        isSuccess = errorAndSuccess().second
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(
                        background,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .border(
                        width = 0.5.dp,
                        color = borderColor,
                        shape = RoundedCornerShape(6.dp)
                    )
                    .onFocusChanged { focusState -> isFocused = focusState.isFocused }
                    .padding(horizontal = 16.dp, vertical = 8.dp), // Internal padding for a better look
                cursorBrush = SolidColor(if (isError) appTheme.colors.error else appTheme.colors.textFieldBorder),
                textStyle = TextStyle(
                    color = textColor,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,  // Align text to the start (left)
                    shadow = Shadow(color = appTheme.colors.textLabelOnMainBg, blurRadius = 1f) // Subtle shadow for text
                ),
                singleLine = singleLine,
                decorationBox = { innerTextField ->
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        if (textState.isEmpty()) {
                            Text(
                                text = "Enter $label",
                                color = appTheme.colors.textLabelOnMainBg,
                                style = MaterialTheme.typography.subtitle2,
                                textAlign = TextAlign.Start // Placeholder text aligned to the start
                            )
                        }
                        innerTextField() // The actual text field
                    }
                }
            )
        }

    }
}