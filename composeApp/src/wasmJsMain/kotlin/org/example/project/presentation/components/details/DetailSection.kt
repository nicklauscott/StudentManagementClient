package org.example.project.presentation.components.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.student.Gender
import org.example.project.domain.model.student.Student
import org.example.project.domain.model.student.StudentStatus
import org.example.project.domain.model.student.toDepartment
import org.example.project.domain.toLocalDateTime
import org.example.project.domain.toTriple
import org.example.project.presentation.components.ButtonIcon
import org.example.project.presentation.components.CustomButton
import org.example.project.presentation.components.HeaderText

@Composable
fun DetailSection(
    modifier: Modifier = Modifier, student: Student, enabled: Boolean,
    updatedFailed: Boolean, saving: Boolean, onClickSave: (Student) -> Unit = {}
) {

    val scrollState = rememberScrollState()

    var editStudent by remember { mutableStateOf(false) }
    var firstNameTextState by remember { mutableStateOf(TextFieldValue(student.firstName)) }
    var lastNameTextState by remember { mutableStateOf(TextFieldValue(student.lastName)) }
    var emailTextState by remember { mutableStateOf(TextFieldValue(student.email)) }
    var mobileTextState by remember { mutableStateOf(TextFieldValue(student.guardianMobile)) }
    var addressTextState by remember { mutableStateOf(TextFieldValue(student.address)) }
    var selectedDateOfBirth by remember {
        mutableStateOf(Triple(
            student.dateOfBirth.year, student.dateOfBirth.monthNumber, student.dateOfBirth.dayOfMonth
        ))
    }
    var selectedGender by remember { mutableStateOf(student.gender) }
    var selectedStatus by remember { mutableStateOf(student.status) }
    var selectedDepartment by remember { mutableStateOf(toDepartment(student.department)) }
    var selectedProgram by remember { mutableStateOf(student.program) }
    var selectedEnrollmentDate by remember {
        mutableStateOf(Triple(
            student.dateOfBirth.year, student.dateOfBirth.monthNumber, student.dateOfBirth.dayOfMonth
        ))
    }

    LaunchedEffect(student) {
        editStudent = false
        firstNameTextState = TextFieldValue(student.firstName)
        lastNameTextState = TextFieldValue(student.lastName)
        emailTextState = TextFieldValue(student.email)
        mobileTextState = TextFieldValue(student.guardianMobile)
        addressTextState = TextFieldValue(student.address)
        selectedDateOfBirth = Triple(student.dateOfBirth.year, student.dateOfBirth.monthNumber, student.dateOfBirth.dayOfMonth)
    }

    if (saving) editStudent = false

    Column (
        modifier = modifier
            .verticalScroll(scrollState, enabled = true)
            .padding(vertical = 16.dp, horizontal = 5.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // header
        DetailHeaderSection(
            student = student, enabled = editStudent, saving = saving,
            onClickEdit = { editStudent = true },
            onClickSave = {
                val updatedStudent = student.copy(
                    address = addressTextState.text, dateOfBirth = selectedDateOfBirth.toLocalDateTime(),
                    department = selectedDepartment.toString(), email = emailTextState.text,
                    enrollmentDate = selectedEnrollmentDate.toLocalDateTime(), firstName = firstNameTextState.text,
                    gender = selectedGender, guardianMobile = mobileTextState.text,
                    id = student.id, lastName = lastNameTextState.text,
                    program = selectedProgram, status = selectedStatus
                )
                onClickSave(updatedStudent)
            }
        )



        // personal info
        val nameErrorAndSuccess: (String) -> Pair<Boolean, Boolean> = { (it.length < 3) to (it.length >= 3) }
        PersonalDetailSection(
            canEdit = editStudent, id = student.id, student = student,
            firstNameTextState = firstNameTextState.text,
            onFnChange = { firstNameTextState = TextFieldValue(it) },
            fnErrorAndSuccess = { nameErrorAndSuccess(firstNameTextState.text) },
            lastNameTextState = lastNameTextState.text,
            onLnChange = { lastNameTextState = TextFieldValue(it) },
            lnErrorAndSuccess = { nameErrorAndSuccess(lastNameTextState.text) },
            emailTextState = emailTextState.text,
            onEmailChange = { emailTextState = TextFieldValue(it) },
            emailErrorAndSuccess = {
                val emailPattern = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$")
                !emailTextState.text.matches(emailPattern) to emailTextState.text.matches(emailPattern)
            },
            mobileTextState = mobileTextState.text,
            onMobileChange = {
                if (it.replace("-", "").replace("+", "").all { char -> char.isDigit() }) {
                    mobileTextState = TextFieldValue(it)
                }
            },
            mobileErrorAndSuccess = { (mobileTextState.text.length < 10) to (mobileTextState.text.length >= 10) },
            addressTextState = addressTextState.text,
            onAddressChange = {  addressTextState = TextFieldValue(it) },
            addressErrorAndSuccess = { false to false },
            dateOfBirth = student.dateOfBirth.toTriple(), onDobChange = { y, m, d ->
                /* dateOfBirth = LocalDateTime.parse("$y-$m-$d") */
                selectedDateOfBirth = Triple(y, m, d)
            },
            gender = student.gender.toString(), onGenderChange = { selectedGender = Gender.valueOf(it) },
        )


        // academic info
        AcademicDetailSection(
            canEdit = editStudent,
            id = student.id,
            student = student,
            enrollmentDate = student.enrollmentDate.toTriple(),
            onEnrollmentDateChange = { y, m, d ->
                val newDate = Triple(y, m, d)
                selectedEnrollmentDate = newDate
                newDate.toLocalDateTime()

            },
            status = student.status.toString(),
            onStatusChange = { selectedStatus = StudentStatus.valueOf(it) },
            department = toDepartment(student.department),
            onDepartmentChange = { selectedDepartment = toDepartment(it) },
            program = student.program,
            onProgramChange = { selectedProgram = it }
        )

        Spacer(modifier = Modifier.height(45.dp))

    }
}


@Composable
fun DetailHeaderSection(
    student: Student, enabled: Boolean, saving: Boolean,
    onClickEdit: () -> Unit = {}, onClickSave: () -> Unit = {},
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        HeaderText("${student.firstName} ${student.lastName}")
        Spacer(modifier = Modifier.width(16.dp))
        ButtonIcon(Icons.Filled.Edit, saving = false, onClick = onClickEdit)
        CustomButton(text = "Save", enabled = enabled, saving = saving, onClick = onClickSave)
    }
}
