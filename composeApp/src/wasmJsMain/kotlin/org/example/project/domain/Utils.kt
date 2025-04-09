package org.example.project.domain

import kotlinx.browser.localStorage
import kotlinx.datetime.LocalDateTime
import org.example.project.domain.constant.UserDetailType
import org.example.project.domain.model.student.Course
import org.example.project.domain.model.student.Student
import org.example.project.domain.model.user.User

fun List<Student>.getUniqueCourses(): Set<Course> {
    val courses = mutableListOf<Course>()
    this.forEach { it.course.forEach { course -> courses.add(course.copy(id = 1))} }
    return courses.toSet()
}

fun LocalDateTime.toTriple(): Triple<Int, Int, Int> = Triple(this.year, this.monthNumber, this.dayOfMonth)

fun Triple<Int, Int, Int>.toLocalDateTime(): LocalDateTime {
    val month = if (this.second < 10) "0${this.second}" else this.second
    val day = if (this.third < 10) "0${this.third}" else this.third
    return LocalDateTime.parse("${this.first}-$month-${day}T21:37:14.506589")
}

fun getUserDetail(): User {
    val firstName = localStorage.getItem(UserDetailType.FIRSTNAME.key)
    val lastName = localStorage.getItem(UserDetailType.LASTNAME.key)
    val email = localStorage.getItem(UserDetailType.EMAIL.key)
    return User(
        email = email ?: "",
        firstName = firstName ?: "",
        lastName = lastName ?: "",
    )
}