package org.example.project.presentation.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.*
import org.example.project.domain.alert
import org.example.project.domain.constant.Response
import org.example.project.domain.mapper.toDTO
import org.example.project.domain.model.Student
import org.example.project.domain.repository.Repository
import org.example.project.presentation.screens.home.stateandevent.HomeScreenState
import org.example.project.presentation.screens.home.stateandevent.UiEvent

class HomeScreenManager(private val repository: Repository) {

    private val _homeScreenState: MutableState<HomeScreenState> = mutableStateOf(HomeScreenState())
    val homeScreenState: State<HomeScreenState> = _homeScreenState
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    init { fetchAllStudents() }

    fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.LoadStudents -> {
                val pagingAndSort = homeScreenState.value.pagingAndSort
                _homeScreenState.value = homeScreenState.value.copy(
                    pagingAndSort = pagingAndSort.copy(page = pagingAndSort.page + 1)
                )
                fetchAllStudents()
            }
            is UiEvent.SelectStudent -> loadStudent(event.id)
            UiEvent.DeSelectCourse -> _homeScreenState.value = homeScreenState.value.copy(activeCourse = null)
            is UiEvent.SelectCourse -> {
                _homeScreenState.value = homeScreenState.value.copy(
                    activeCourse = homeScreenState.value.activeStudent?.course?.find { it.id == event.id }
                )
            }
            is UiEvent.UpdateStudent -> updateStudent(event.student)
            is UiEvent.AddCourse -> enrollStudentToCourse(event.courseCodes)
        }
    }

    private fun enrollStudentToCourse(courseCodes: List<String>) {
        _homeScreenState.value = homeScreenState.value.copy(enrollingStudentToCourse = true)
        scope.launch {
            val courses = courseCodes.mapNotNull { courseCode ->
                homeScreenState.value.allCourse.find { it.courseCode == courseCode }
            }
            homeScreenState.value.activeStudent?.id?.let {
                val response = repository.registerCourseForStudent(it.toLong(), courses)

                if (response is Response.Failure) {
                    _homeScreenState.value = homeScreenState.value.copy(
                        enrollingStudentToCourse = false,
                        numberOfCoursesThatFailedToBeEnrolled = courses.size
                    )
                    return@launch
                }

                if (response is Response.Success) {
                    val updatedStudents = homeScreenState.value.students.toMutableList()
                    updatedStudents.removeAll { id -> id.id == (homeScreenState.value.activeStudent?.id ?: -1) }
                    updatedStudents.add(response.data)

                    val codes = courses.map { codes -> codes.courseCode }
                    val successfulCourses = response.data.course.filter { course -> codes.contains(course.courseCode) }

                    _homeScreenState.value = homeScreenState.value.copy(
                        enrollingStudentToCourse = false,
                         numberOfCoursesThatFailedToBeEnrolled = successfulCourses.size,
                        students = updatedStudents,
                        activeStudent = response.data,
                        updateStudent = response.data
                    )
                }
            }
        }
    }

    private fun updateStudent(student: Student) {
        _homeScreenState.value = homeScreenState.value.copy(updatingStudent = true)
        scope.launch {
            val response = repository.updateStudent(student.toDTO())
            if (response is Response.Failure) {
                _homeScreenState.value = homeScreenState.value.copy(
                    updatingStudent = false,
                    updatingStudentWasSuccessful = false
                )
                alert("Error!. Failed to update student")
                return@launch
            }

            if (response is Response.Success) {
                val updatedStudents = homeScreenState.value.students.toMutableList()
                updatedStudents.removeAll { id -> id.id == (homeScreenState.value.activeStudent?.id ?: -1) }
                updatedStudents.add(response.data)
                _homeScreenState.value = homeScreenState.value.copy(
                    updatingStudent = false,
                    updatingStudentWasSuccessful = true,
                    students = updatedStudents,
                    updateStudent = response.data,
                    activeStudent = response.data
                )
            }
        }
    }

    private fun loadStudent(id: Int) {
        _homeScreenState.value = homeScreenState.value.copy(loadingActiveStudent = true)
        scope.launch {
            val student = homeScreenState.value.students.find { it.id == id }
            _homeScreenState.value = homeScreenState.value.copy(
                activeStudent = student,
                updateStudent = student,
                loadingActiveStudent = false
            )
        }
    }

    private fun fetchAllStudents() {
        _homeScreenState.value = homeScreenState.value.copy(fetchingStudents = true)
        scope.launch {
            val response = repository.getStudents(
                page = homeScreenState.value.pagingAndSort.page,
                homeScreenState.value.pagingAndSort.size,
                sortBy = homeScreenState.value.pagingAndSort.sortBy,
                orderBy = homeScreenState.value.pagingAndSort.sortOrder
            )
            if (response is Response.Failure) {
                _homeScreenState.value = homeScreenState.value.copy(
                    fetchingStudents = false,
                    errorFetchingStudents = response.message
                )
                return@launch
            }
            if (response is Response.Success) {
                _homeScreenState.value = homeScreenState.value.copy(
                    fetchingStudents = false,
                    students = homeScreenState.value.students + response.data.second,
                    pagingAndSort = response.data.first
                )
            }
        }

    }
}