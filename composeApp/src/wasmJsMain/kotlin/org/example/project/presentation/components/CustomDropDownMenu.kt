package org.example.project.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.presentation.screens.home.components.InfoCell
import org.example.project.presentation.theme.LocalAppTheme

@Composable
fun CustomEnhanceDropDownMenu(
    modifier: Modifier, id: Int, label: String, currentItem: String?,
    canEdit: Boolean, isError: Boolean = false, isSuccess: Boolean = false,
    items: List<String>, onItemSelected: (String) -> Unit
) {
    val appTheme = LocalAppTheme.current

    Column(
        modifier = modifier.fillMaxWidth().padding(10.dp).padding(top = 5.dp),
        horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Center
    ) {
        InfoCell(text = label, style = MaterialTheme.typography.subtitle2, color = appTheme.colors.textOnMainBg)
        Spacer(modifier = Modifier.height(2.dp))

        CustomDropDownMenu(
            modifier =Modifier,
            id = id, label = "Gender", currentItem = currentItem,
            canEdit = canEdit, canHaveError = false, isError = false, isSuccess = false,
            items = items, onItemSelected = onItemSelected
        )
    }
}



@Composable
fun CustomDropDownMenu(
    modifier: Modifier, id: Int, label: String, currentItem: String?,
    canEdit: Boolean, canHaveError: Boolean, isError: Boolean = false, isSuccess: Boolean = false,
    items: List<String>, onItemSelected: (String) -> Unit
) {
    val appTheme = LocalAppTheme.current
    val selectedItem = remember { mutableStateOf(currentItem ?: "Select $label") }
    val expandItems = remember { mutableStateOf(false) }

    LaunchedEffect(id) {
        expandItems.value = false
        selectedItem.value = currentItem ?: "Select Label"
    }

    val background by animateColorAsState(
        targetValue = if (canEdit) {
            if (expandItems.value) appTheme.colors.sidePanelBackGround else appTheme.colors.mainBackGround4
        } else appTheme.colors.mainBackGround5
    )

    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.TopStart) {
        Card(
            shape = RoundedCornerShape(6.dp),
            elevation = 12.dp,
            backgroundColor = background,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = modifier.fillMaxWidth()
                    .border(
                        width = 0.5.dp,
                        color = appTheme.colors.borderStroke,
                        shape = RoundedCornerShape(6.dp)
                    )
                    .clickable(enabled = canEdit) { expandItems.value = true }
                    .height(52.dp)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = modifier.fillMaxWidth()
                        .padding(horizontal = 5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        selectedItem.value.replace("_", " "),
                        style = TextStyle(
                            color = appTheme.colors.textOnMainBg,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Start,
                            shadow = Shadow(color = appTheme.colors.textLabelOnMainBg, blurRadius = 1f)
                        )
                    )

                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown, contentDescription = null,
                        tint = appTheme.colors.onBackGround2
                    )

                }
                DropdownMenu(
                    modifier = Modifier.background(appTheme.colors.mainBackGround5),
                    expanded = expandItems.value, onDismissRequest = { expandItems.value = false }) {
                    items.forEach { item ->
                        DropdownMenuItem(
                            onClick = {
                                selectedItem.value = item
                                expandItems.value = false
                                onItemSelected(selectedItem.value)
                            },
                            modifier = Modifier.background(appTheme.colors.sidePanelBackGround),
                            enabled = true,
                            contentPadding = PaddingValues(3.dp),
                            interactionSource = MutableInteractionSource(),
                            content = {
                                Text(
                                    text = item.replace("_", " "),
                                    color = appTheme.colors.textLabelOnMainBg,
                                    style = MaterialTheme.typography.subtitle2,
                                    textAlign = TextAlign.Start
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}