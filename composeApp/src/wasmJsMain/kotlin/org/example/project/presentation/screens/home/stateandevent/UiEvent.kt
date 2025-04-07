package org.example.project.presentation.screens.home.stateandevent

import org.example.project.domain.model.student.Student


sealed interface UiEvent {
    data object LoadStudents: UiEvent
    data class SelectStudent(val id: Int): UiEvent
    data class SelectCourse(val id: Int): UiEvent
    class AddCourse(val courseCodes: List<String>) : UiEvent
    data object DeSelectCourse: UiEvent
    data class UpdateStudent(val student: Student): UiEvent
}

