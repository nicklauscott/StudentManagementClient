package org.example.project.presentation.screens.home.stateandevent

import org.example.project.domain.getUniqueCourses
import org.example.project.domain.model.student.Course
import org.example.project.domain.model.student.PagingAndSort
import org.example.project.domain.model.student.Student

data class HomeScreenState(
    val students: List<Student> = emptyList(),
    val pagingAndSort: PagingAndSort = PagingAndSort(),
    val fetchingStudents: Boolean = false,
    val errorFetchingStudents: String? = null,

    val activeStudent: Student? = null,
    val loadingActiveStudent: Boolean = false,

    val activeCourse: Course? = null,

    val updateStudent: Student? = null,

    val updatingStudent: Boolean = false,
    val updatingStudentWasSuccessful: Boolean? = null,

    val enrollingStudentToCourse: Boolean = false,
    val numberOfCoursesThatFailedToBeEnrolled: Int = 0
) {
    val allCourse: Set<Course> by lazy { students.getUniqueCourses() }
}



