package org.example.project.presentation.components.details

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.student.Department
import org.example.project.domain.model.student.Student
import org.example.project.domain.model.student.StudentStatus
import org.example.project.presentation.components.CustomDatePicker
import org.example.project.presentation.components.CustomEnhanceDropDownMenu
import org.example.project.presentation.screens.home.components.InfoCell
import org.example.project.presentation.theme.LocalAppTheme

@Composable
fun AcademicDetailSection(
    canEdit: Boolean, id: Int, student: Student,
    enrollmentDate: Triple<Int, Int, Int>, onEnrollmentDateChange: (Int, Int, Int) -> Unit,
    status: String, onStatusChange: (String) -> Unit,
    department: Department, onDepartmentChange: (String) -> Unit,
    program: String, onProgramChange: (String) -> Unit,
) {

    var programs by remember { mutableStateOf(department.programs) }

    LaunchedEffect(id) {
        programs = department.programs
    }

    Column(
        modifier = Modifier
            .padding(vertical = 2.5.dp)
            .border(
                width = 0.5.dp,
                color = LocalAppTheme.current.colors.borderStroke,
                shape = RoundedCornerShape(6.dp)
            )
            .padding(start = 5.dp, end = 5.dp, top = 10.dp),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InfoCell(text = "Academic Info", style = MaterialTheme.typography.h4, color = LocalAppTheme.current.colors.textOnMainBg)

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            CustomDatePicker(
                modifier = Modifier.weight(0.47f), id = id, canEdit = canEdit, label = "Enrollment Date",
                initialDate = enrollmentDate, onDateSelected = onEnrollmentDateChange
            )
            Spacer(modifier = Modifier.weight(0.06f))
            CustomEnhanceDropDownMenu(
                modifier = Modifier.weight(0.47f), id = id,
                label = "Status", currentItem = status, canEdit = canEdit,
                isError = false, isSuccess = false, items = StudentStatus.entries.map { it.toString() },
                onItemSelected = onStatusChange
            )
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            CustomEnhanceDropDownMenu(
                modifier = Modifier.weight(0.47f), id = id,
                label = "Department", currentItem = department.toString(), canEdit = canEdit,
                isError = false, isSuccess = false, items = Department.entries.map { it.toString() },
                onItemSelected = onStatusChange
            )
            Spacer(modifier = Modifier.weight(0.06f))
            CustomEnhanceDropDownMenu(
                modifier = Modifier.weight(0.47f), id = id,
                label = "Program", currentItem = program, canEdit = canEdit,
                isError = false, isSuccess = false, items = programs,
                onItemSelected = onStatusChange
            )
        }
    }
}
