package org.example.project.presentation.components.details

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.Gender
import org.example.project.domain.model.Student
import org.example.project.presentation.components.CustomDatePicker
import org.example.project.presentation.components.CustomEnhanceDropDownMenu
import org.example.project.presentation.components.EnhacedTextAndTextFieldCell
import org.example.project.presentation.screens.home.components.InfoCell
import org.example.project.presentation.theme.LocalAppTheme

@Composable
fun PersonalDetailSection(
    canEdit: Boolean, id: Int, student: Student,
    firstNameTextState: String, onFnChange: (String) -> Unit, fnErrorAndSuccess: () -> Pair<Boolean, Boolean>,
    lastNameTextState: String, onLnChange: (String) -> Unit, lnErrorAndSuccess: () -> Pair<Boolean, Boolean>,
    emailTextState: String, onEmailChange: (String) -> Unit, emailErrorAndSuccess: () -> Pair<Boolean, Boolean>,
    mobileTextState: String, onMobileChange: (String) -> Unit, mobileErrorAndSuccess: () -> Pair<Boolean, Boolean>,
    addressTextState: String, onAddressChange: (String) -> Unit, addressErrorAndSuccess: () -> Pair<Boolean, Boolean>,
    dateOfBirth: Triple<Int, Int, Int>, onDobChange: (Int, Int, Int) -> Unit,
    gender: String, onGenderChange: (String) -> Unit,
) {

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
        InfoCell(text = "Person Info", style = MaterialTheme.typography.h4, color = LocalAppTheme.current.colors.textOnMainBg)

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            val modifier = Modifier
            // first name
            EnhacedTextAndTextFieldCell(
                modifier = modifier.weight(0.47f),
                id = id, label = "First Name",
                textState = firstNameTextState,
                canEdit = canEdit,
                singleLine = false,
                onTextChange = onFnChange,
                errorAndSuccess = fnErrorAndSuccess
            )

            Spacer(modifier = modifier.weight(0.06f))

            // last name
            EnhacedTextAndTextFieldCell(
                modifier = modifier.weight(0.47f),
                id = id, label = "Last Name",
                textState = lastNameTextState,
                canEdit = canEdit,
                singleLine = false,
                onTextChange = onLnChange,
                errorAndSuccess = lnErrorAndSuccess
            )
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            CustomDatePicker(
                modifier = Modifier.weight(0.47f), id = id, canEdit = canEdit, label = "Date Of Birth",
                initialDate = dateOfBirth, onDateSelected = onDobChange
            )
            Spacer(modifier = Modifier.weight(0.06f))
            CustomEnhanceDropDownMenu(
                modifier = Modifier.weight(0.47f), id = id,
                label = "Gender", currentItem = gender, canEdit = canEdit,
                isError = false, isSuccess = false, items = Gender.entries.map { it.toString() },
                onItemSelected = onGenderChange
            )
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            val modifier = Modifier
            // date of birth
            EnhacedTextAndTextFieldCell(
                modifier = modifier.weight(0.47f),
                id = id, label = "Email",
                textState = emailTextState,
                canEdit = canEdit,
                singleLine = false,
                onTextChange = onEmailChange,
                errorAndSuccess = emailErrorAndSuccess
            )

            Spacer(modifier = modifier.weight(0.06f))

            // guardian mobile
            EnhacedTextAndTextFieldCell(
                modifier = modifier.weight(0.47f),
                id = id, label = "Guardian Mobile",
                textState = mobileTextState,
                canEdit = canEdit,
                singleLine = false,
                onTextChange = onMobileChange,
                errorAndSuccess = mobileErrorAndSuccess
            )
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            // address
            EnhacedTextAndTextFieldCell(
                modifier = Modifier.fillMaxWidth(),
                id = id, label = "Address",
                textState = addressTextState,
                canEdit = canEdit,
                singleLine = true,
                onTextChange = onAddressChange,
                errorAndSuccess = addressErrorAndSuccess
            )
        }
    }
}


