package org.example.project.presentation.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.domain.model.student.Student
import org.example.project.domain.model.student.StudentStatus
import org.example.project.presentation.components.InfoCell
import org.example.project.presentation.theme.LocalAppTheme

@Composable
fun StudentCell(modifier: Modifier = Modifier, student: Student, onClick: (Int) -> Unit) {
    val appTheme = LocalAppTheme.current
    Row(
        modifier = modifier.fillMaxWidth().padding(vertical = 4.dp, horizontal = 1.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(appTheme.colors.mainBackGround3)
            .clickable { onClick(student.id) }
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

        }

        Spacer(modifier = Modifier.width(18.dp))

        Column(
            modifier = Modifier.padding(vertical = 1.dp)
        ) {
            InfoCell(
                text = "${student.firstName} ${student.lastName}",
                style = MaterialTheme.typography.subtitle2,
                color = appTheme.colors.onBackGround2
            )
            InfoCell(
                text = student.department,
                style = MaterialTheme.typography.caption,
                color = appTheme.colors.onBackGround1
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    student.gender.toString(),
                    style = MaterialTheme.typography.caption,
                    color = appTheme.colors.onBackGround2
                )
                Spacer(modifier = Modifier.width(8.dp))
                StudentStatusCell(student.status)
            }
        }
    }
}

@Composable
fun StudentStatusCell(status: StudentStatus) {
    val colors = LocalAppTheme.current.colors
    val color = when (status) {
        StudentStatus.ACTIVE -> colors.success
        StudentStatus.GRADUATED -> colors.okay
        StudentStatus.SUSPENDED -> colors.warning
        StudentStatus.DROPPED_OUT -> colors.error
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(2.dp))
            .background(color.copy(0.8f))
            .padding(2.dp)
    ) {
        Text(
            status.toString().replace("_", " "),
            style = MaterialTheme.typography.caption,
            color = colors.badgeText.copy(alpha = 0.8f), fontSize = 10.sp
        )
    }
}