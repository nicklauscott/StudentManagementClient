package org.example.project.presentation.screens.sidepanel

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.domain.model.user.User
import org.example.project.domain.reload
import org.example.project.presentation.components.InfoCell
import org.example.project.presentation.screens.sidepanel.stateandevent.SidePanelEvent
import org.example.project.presentation.theme.LocalAppTheme
import org.koin.core.context.GlobalContext

@Composable
fun SidePanelScreen() {
    val manager = remember { GlobalContext.get().get<SidePanelScreenManager>() }

    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp, horizontal = 5.dp)) {

        SidePanelHeader(
            manager.state.value.currentUser,
            onSignOutClick = { manager.onEvent(SidePanelEvent.SignOut) }
        )

        SidePanleCell(label = "Home", leadingIcon = Icons.Filled.Home) { reload() }
        SidePanleCell(label = "Search", leadingIcon = Icons.Filled.Search) {}
        SidePanleCell(label = "Create Student", leadingIcon = Icons.Filled.Add) {}
        SidePanleCell(label = "Create Course", leadingIcon = Icons.Filled.Create) {}
    }
}

@Composable
fun SidePanelHeader(user: User, onSignOutClick: () -> Unit) {
    val appTheme = LocalAppTheme.current
    val expanded = remember { mutableStateOf(false) }
    SidePanleCell(
        modifier = Modifier.padding(bottom = 16.dp),
        label = " Hi, " + user.firstName,
        style = TextStyle(
            fontSize = 20.sp, fontWeight = FontWeight.Bold,
            color = appTheme.colors.textOnMainBg
        ),
        textColor = Color.White.copy(alpha = 0.7f),
        trailingIcon = Icons.Filled.ArrowDropDown,
        leadingIcon = Icons.Filled.AccountBox
    ) { expanded.value = !expanded.value }
    AnimatedVisibility(expanded.value) {
        val backAndForeGround = appTheme.colors.mainBackGround4 to appTheme.colors.mainBackGround2
        Surface(
            modifier = Modifier.width(500.dp).padding(8.dp)
                .graphicsLayer { translationY = -50f },
            border = BorderStroke(width = 1.dp, color = appTheme.colors.mainBackGround1),
            elevation = 6.dp,
            color = appTheme.colors.mainBackGround4,
            shape = RoundedCornerShape(6.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 5.dp, vertical = 10.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    InfoCell(
                        text = user.firstName + " " + user.lastName,
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                        color = Color.White.copy(alpha = 0.7f)
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Divider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 2.dp,
                        color = appTheme.colors.sidePanelBackGround
                    )

                    Spacer(modifier = Modifier.height(5.dp))
                }

                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    InfoCell(
                        text = user.email,
                        style = MaterialTheme.typography.body2,
                        color = appTheme.colors.onBackGround2
                    )
                }

                SidePanleCell(
                    label = "Edit profile",
                    backAndForeGround = backAndForeGround,
                    trailingIcon = Icons.Filled.Edit
                ) {}

                SidePanleCell(
                    label = "Sign out",
                    backAndForeGround = backAndForeGround,
                    trailingIcon = Icons.AutoMirrored.Filled.ExitToApp
                ) { onSignOutClick() }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SidePanleCell(
    modifier: Modifier = Modifier,
    label: String, style: TextStyle = MaterialTheme.typography.body2,
    clickable: Boolean = true,
    textColor: Color = LocalAppTheme.current.colors.onBackGround2,
    leadingIcon: ImageVector? = null, trailingIcon: ImageVector? = null,
    backAndForeGround: Pair<Color, Color> =
        LocalAppTheme.current.colors.sidePanelBackGround to LocalAppTheme.current.colors.mainBackGround1,
    onClick: () -> Unit = {}
) {
    val appTheme = LocalAppTheme.current
    val isHovered = remember { mutableStateOf(false) }
    Row(
        modifier = modifier.fillMaxWidth()
            .padding(vertical = 1.dp)
            .clip(RoundedCornerShape(4.dp))
            .clickable(clickable) { onClick() }
            .onPointerEvent(PointerEventType.Enter) {
                isHovered.value = true
            }
            .onPointerEvent(PointerEventType.Exit) {
                isHovered.value = false
            }
            .background(
                if (!isHovered.value) backAndForeGround.first
                else backAndForeGround.second
            )
            .padding(vertical = 1.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Row(
            modifier = Modifier.weight(0.7f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            leadingIcon?.let {
                Icon(
                    leadingIcon, contentDescription = null,
                    tint = appTheme.colors.onBackGround2,
                    modifier = Modifier.size(18.dp)
                )
            }

            InfoCell(
                text = label, style = style, horizontalPadding = 3.dp,
                color = textColor, verticalPadding = 0.dp
            )
        }

        Row(
            modifier = Modifier.weight(0.3f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            trailingIcon?.let {
                Icon(
                    trailingIcon, contentDescription = null,
                    tint = appTheme.colors.onBackGround2,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

