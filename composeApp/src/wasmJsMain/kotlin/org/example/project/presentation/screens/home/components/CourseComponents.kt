package org.example.project.presentation.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.example.project.domain.model.student.Course
import org.example.project.presentation.components.ButtonIcon
import org.example.project.presentation.components.HeaderText
import org.example.project.presentation.components.InfoCell
import org.example.project.presentation.theme.LocalAppTheme
import org.jetbrains.compose.resources.painterResource
import studentmanagementclient.composeapp.generated.resources.Res
import studentmanagementclient.composeapp.generated.resources.user_icon

@Composable
fun CourseDetailDialog(course: Course, onDismiss: () -> Unit) {
    val appTheme = LocalAppTheme.current
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier.size(500.dp)
                .background(appTheme.colors.sidePanelBackGround)
        ) {

        }
    }
}

@Composable
fun AddCourseDetailDialog(
    course: Set<Course>,
    onClickCourse: (String) -> Unit,
    onEnrollCourse: () -> Unit,
    onDismiss: () -> Unit
) {
    val appTheme = LocalAppTheme.current
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier.size(500.dp)
                .background(appTheme.colors.sidePanelBackGround)
        ) {

        }
    }
}

@Composable
fun CourseSection(
    modifier: Modifier = Modifier,
    studentFullName: String, course: List<Course>,
    onClickAdd: () -> Unit = {},
    onClickCourse: (Int) -> Unit = {}
) {
    Column(
        modifier = modifier.padding(top = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = modifier.fillMaxWidth().weight(0.1f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HeaderText(text = "$studentFullName Courses")
            ButtonIcon(Icons.Filled.Add, saving = false, onClick = onClickAdd)
        }


        LazyVerticalGrid(
            modifier = Modifier.weight(0.9f),
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(horizontal = 3.dp, vertical = 16.dp),
        ) {
            items(course.size) { index ->
                CourseCell(course[index]) { courseId -> onClickCourse(courseId) }
            }
        }
    }
}

@Composable
fun CourseCell(course: Course, onClick: (Int) -> Unit) {
    val appTheme = LocalAppTheme.current
    val backgroundColor = remember { mutableStateOf(
        when (course.creditHours) {
            in 0..20 -> appTheme.colors.mutedError
            in 20..40 -> appTheme.colors.mutedWarning
            in 40..60 -> appTheme.colors.mutedPurple
            in 60..80 -> appTheme.colors.mutedOkay
            else -> appTheme.colors.mutedSuccess
        }
    ) }
    val iconColor = remember { mutableStateOf(
        when (course.creditHours) {
            in 0..20 -> appTheme.colors.error
            in 20..40 -> appTheme.colors.warning
            in 40..60 -> appTheme.colors.purple
            in 60..80 -> appTheme.colors.okay
            else -> appTheme.colors.success
        }
    ) }

    LaunchedEffect(course) {
        backgroundColor.value = when (course.creditHours) {
            in 0..20 -> appTheme.colors.mutedError
            in 20..40 -> appTheme.colors.mutedWarning
            in 40..60 -> appTheme.colors.mutedPurple
            in 60..80 -> appTheme.colors.mutedOkay
            else -> appTheme.colors.mutedSuccess
        }
        iconColor.value = when (course.creditHours) {
            in 0..20 -> appTheme.colors.error
            in 20..40 -> appTheme.colors.warning
            in 40..60 -> appTheme.colors.purple
            in 60..80 -> appTheme.colors.okay
            else -> appTheme.colors.success
        }
    }

    Card(
        backgroundColor = backgroundColor.value,
        modifier = Modifier
            .padding(5.dp)
            .height(160.dp).width(150.dp)
            .clickable { onClick(course.id) },
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column {

            Box(modifier = Modifier.weight(0.3f).fillMaxHeight().graphicsLayer {
                renderEffect = BlurEffect(radiusX = 5f, radiusY = 5f)
            })

            Column(
                modifier = Modifier
                    .weight(0.7f)
                    .fillMaxWidth()
                    .background(appTheme.colors.mainBackGround2)
                    .padding(5.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {

                Image(
                    painterResource(Res.drawable.user_icon), null,
                    colorFilter = ColorFilter.tint(iconColor.value),
                    modifier = Modifier.size(25.dp)
                        .graphicsLayer {
                            translationY = -40f
                        }
                )

                InfoCell(
                    text = course.courseName,
                    style = MaterialTheme.typography.subtitle1,
                    color = appTheme.colors.onBackGround3
                )

                InfoCell(
                    text = course.description.take(35) + "...",
                    style = MaterialTheme.typography.caption,
                    color = appTheme.colors.onBackGround3.copy(alpha = 0.7f)
                )

                InfoCell(
                    text = "${course.creditHours} Credit Hours",
                    style = MaterialTheme.typography.body2,
                    color = appTheme.colors.onBackGround2
                )
            }
        }
    }
}