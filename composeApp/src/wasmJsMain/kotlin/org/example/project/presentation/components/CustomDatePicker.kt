package org.example.project.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.presentation.screens.home.components.InfoCell
import org.example.project.presentation.theme.LocalAppTheme

@Composable
fun CustomDatePicker(
    modifier: Modifier, id: Int, canEdit: Boolean, label: String,
    initialDate: Triple<Int, Int, Int> = Triple(2025, 11, 15),
    onDateSelected: (Int, Int, Int) -> Unit
) {
    val appTheme = LocalAppTheme.current
    val years = (2000..2030).toList()
    val months = (1..12).toList()
    val days = remember { mutableStateOf((1..31).toList()) }

    var selectedDate by remember { mutableStateOf(initialDate) }
    var isValidDay by remember { mutableStateOf(true) }

    LaunchedEffect(initialDate, id) {
        days.value = (1..daysInMonth(initialDate.first, initialDate.second)).toList()
        selectedDate = initialDate
        isValidDay = true
    }

    Column(
        modifier = modifier.fillMaxWidth().padding(10.dp).padding(top = 5.dp),
        horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Center
    ) {
        InfoCell(text = label, style = MaterialTheme.typography.subtitle2, color = appTheme.colors.textOnMainBg)
        Spacer(modifier = Modifier.height(2.dp))

        // Date picker
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopStart) {
            Card(
                shape = RoundedCornerShape(6.dp),
                elevation = 12.dp, // Enhanced shadow
                backgroundColor = appTheme.colors.mainBackGround5,
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier.fillMaxSize()
                        .border(
                            width = 0.5.dp,
                            color = appTheme.colors.borderStroke,
                            shape = RoundedCornerShape(6.dp)
                        ),
                    verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
                ) {
                    val dropDownMenuModifier = modifier.height(52.dp)
                    CustomDropDownMenu(
                        modifier = dropDownMenuModifier.weight(0.5f),
                        id = id,
                        label = "Year",
                        currentItem = initialDate.first.toString(),
                        canEdit = canEdit,
                        canHaveError = false,
                        isError = false,
                        isSuccess = false,
                        items = years.map { it.toString() },
                        onItemSelected = {
                            val selectedYear = it.toInt()
                            days.value = (1..daysInMonth(selectedYear, initialDate.second)).toList()
                            isValidDay = validateDate(selectedYear, initialDate.second, initialDate.third)
                            selectedDate = selectedDate.copy(first = selectedYear)
                            onDateSelected(selectedDate.first, selectedDate.second, selectedDate.third)
                        }
                    )

                    CustomDropDownMenu(
                        modifier = dropDownMenuModifier.weight(0.25f),
                        id = id,
                        label = "Month",
                        currentItem = initialDate.second.toString(),
                        canEdit = canEdit,
                        canHaveError = false,
                        isError = false,
                        isSuccess = false,
                        items = months.map { it.toString() },
                        onItemSelected = {
                            val selectedMonth = it.toInt()
                            days.value = (1..daysInMonth(initialDate.first, selectedMonth)).toList()
                            isValidDay = validateDate(initialDate.first, selectedMonth, initialDate.third)
                            selectedDate = selectedDate.copy(second = selectedMonth)
                            onDateSelected(selectedDate.first, selectedDate.second, selectedDate.third)
                        }
                    )

                    CustomDropDownMenu(
                        modifier = dropDownMenuModifier.weight(0.25f),
                        id = id,
                        label = "Day",
                        currentItem = initialDate.third.toString(),
                        canEdit = canEdit,
                        canHaveError = true,
                        isError = !isValidDay,
                        isSuccess = isValidDay,
                        items = days.value.map { it.toString() },
                        onItemSelected = {
                            val selectedDay = it.toInt()
                            days.value = (1..daysInMonth(initialDate.first, initialDate.second)).toList()
                            isValidDay = validateDate(initialDate.first, initialDate.second, selectedDay)
                            selectedDate = selectedDate.copy(third = selectedDay)
                            onDateSelected(selectedDate.first, selectedDate.second, selectedDate.third)
                        },
                    )

                }
            }
        }

    }

}


fun daysInMonth(year: Int, month: Int): Int {
    return  if (month == 2) {
        if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) 29
        else 28
    }
    else if (month in listOf(4, 6, 9, 11)) 30
    else 31
}

val validateDate: (Int, Int, Int) -> Boolean = { year, month, day ->
    if (month == 2) {
        if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) day < 30
        else day < 29
    }
    else if (month in listOf(4, 6, 9, 11)) day <= 30
    else true
}