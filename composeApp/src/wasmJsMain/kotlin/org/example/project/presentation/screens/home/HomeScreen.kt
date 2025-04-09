package org.example.project.presentation.screens.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.browser.window
import org.example.project.domain.model.student.Student
import org.example.project.presentation.components.*
import org.example.project.presentation.components.details.DetailSection
import org.example.project.presentation.screens.home.components.*
import org.example.project.presentation.screens.home.stateandevent.UiEvent
import org.example.project.presentation.theme.LocalAppTheme
import org.koin.core.context.GlobalContext
import studentmanagementclient.composeapp.generated.resources.Res
import studentmanagementclient.composeapp.generated.resources.course_icon
import studentmanagementclient.composeapp.generated.resources.detail_icon

@Composable
fun HomeScreen() {
    val appTheme = LocalAppTheme.current
    val homeScreenManager = remember { GlobalContext.get().get<HomeScreenManager>() }
    val state = homeScreenManager.homeScreenState.value
    var lastScrollIndex by remember { mutableStateOf(0) }
    var addCourse by remember { mutableStateOf(false) }
    var selectedCourses by remember { mutableStateOf(emptyList<String>()) }

    if (state.activeCourse != null) {
        CourseDetailDialog(state.activeCourse) {
            homeScreenManager.onEvent(UiEvent.DeSelectCourse)
        }
    }

    if (addCourse) {
        AddCourseDetailDialog(
            course = state.allCourse,
            onClickCourse = { selectedCourses = selectedCourses + it },
            onEnrollCourse = { homeScreenManager.onEvent(UiEvent.AddCourse(selectedCourses)) }
        ) { addCourse = false }
    }

    MaterialTheme {
        Row(modifier = Modifier.fillMaxSize()) {
            // student list
            Box(
                modifier = Modifier
                .fillMaxHeight()
                .weight(0.25f),
                contentAlignment = Alignment.Center
            ) {
                when {

                    state.fetchingStudents -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 8.dp),
                            verticalArrangement = Arrangement.Top
                        ) {
                            repeat(8) {
                                LoadingStudentCell()
                            }
                        }
                    }

                    !state.fetchingStudents && state.errorFetchingStudents != null -> {
                        Box(
                            modifier = Modifier.fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Oops! Couldn't load students", fontSize = 18.sp,
                                color = appTheme.colors.onBackGround2
                            )
                        }
                    }

                    !state.fetchingStudents && state.errorFetchingStudents == null -> {
                        if (state.students.isEmpty()) {
                            Box(
                                modifier = Modifier.fillMaxHeight(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Oops! Couldn't load students", fontSize = 18.sp,
                                    color = appTheme.colors.onBackGround2
                                )
                            }
                            return@Row
                        }

                        LazyColumn(
                            modifier = Modifier.fillMaxHeight(),
                            contentPadding = PaddingValues(horizontal = 8.dp)
                        ) {
                            items(state.students.size, key = { it }) {
                                StudentCell(student = state.students[it]) { studentId ->
                                    homeScreenManager.onEvent(UiEvent.SelectStudent(studentId))
                                }

                                if (it == state.students.size - 1 && !state.fetchingStudents) {
                                    lastScrollIndex = it
                                    homeScreenManager.onEvent(UiEvent.LoadStudents)
                                    window.setTimeout({
                                        scrollToIndex(lastScrollIndex)
                                    }, 10)
                                }
                            }

                            if (state.fetchingStudents) {
                                item {
                                    CircularProgressIndicator(
                                        modifier = Modifier.fillMaxWidth().padding(16.dp)
                                    )
                                }
                            }
                        }

                    }

                }
            }


            // active student info
            Column(modifier = Modifier.fillMaxHeight().weight(0.75f)) {
                when {
                    state.students.isNotEmpty() && state.activeStudent == null
                            && !state.loadingActiveStudent -> {
                        NoActiveStudent()
                    }

                    state.loadingActiveStudent -> {
                        Column(modifier = Modifier.fillMaxSize()) {  }
                    }

                    state.activeStudent != null -> {
                        ActiveStudentCell(
                            state.activeStudent,
                            canUpdateStudent = state.activeStudent != state.updateStudent,
                            saving = state.updatingStudent, updatedSuccessFully = state.updatingStudentWasSuccessful ?: true,
                            onClickCourse = { courseId -> homeScreenManager.onEvent(UiEvent.SelectCourse(courseId)) },
                            onClickAddCourse = { addCourse = true},
                            onClickSave = { homeScreenManager.onEvent(UiEvent.UpdateStudent(it)) },
                        )
                    }
                }
            }
        }
    }
}




@Composable
fun ActiveStudentCell(
    student: Student, canUpdateStudent: Boolean, saving: Boolean, updatedSuccessFully: Boolean,
    onClickSave: (Student) -> Unit, onClickCourse: (Int) -> Unit, onClickAddCourse: () -> Unit,
) {
    val appTheme = LocalAppTheme.current
    val studentInfo = remember { mutableStateOf(true) }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        AnimatedContent(
            modifier = Modifier.fillMaxWidth(),
            targetState = studentInfo.value
        ) { targetState ->
            when (targetState) {
                // Details
                true -> {
                    DetailSection(
                        modifier = Modifier.fillMaxSize(),
                        student = student, enabled = canUpdateStudent, saving = saving,
                        updatedFailed = updatedSuccessFully, onClickSave = onClickSave
                    )
                }

                // Course
                false -> {
                    val modifier = Modifier
                        //.matchParentSize()
                        .fillMaxSize()
                    CourseSection(
                        modifier = modifier,
                        studentFullName = "${student.firstName} ${student.lastName}",
                        course = student.course,
                        onClickCourse = onClickCourse,
                        onClickAdd = onClickAddCourse
                    )
                }
            }
        }

        // Bottom nav bar
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            FooterItem(
                modifier = Modifier.weight(0.5f),
                text = "Info", icon = Res.drawable.detail_icon, active = studentInfo.value,
                backgroundColor = appTheme.colors.mainBackGround1
            ) {
                studentInfo.value = true
            }
            FooterItem(
                modifier = Modifier.weight(0.5f),
                text = "Course", icon = Res.drawable.course_icon, active = !studentInfo.value,
                backgroundColor = appTheme.colors.mainBackGround1
            ) {
                studentInfo.value = false
            }
        }
    }
}



// Simulated function to scroll to a position (adjust this as needed)
fun scrollToIndex(index: Int): JsAny? {
    window.scrollBy(0.0, index * 50.0) // Approximate item height * index
    return null
}







