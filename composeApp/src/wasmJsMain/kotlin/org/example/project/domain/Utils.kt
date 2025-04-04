package org.example.project.domain

import kotlinx.datetime.LocalDateTime
import org.example.project.domain.model.Course
import org.example.project.domain.model.Student

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