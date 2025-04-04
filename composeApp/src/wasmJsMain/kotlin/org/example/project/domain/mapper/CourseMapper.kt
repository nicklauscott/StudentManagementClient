package org.example.project.domain.mapper

import org.example.project.data.remote.response.CourseDTO
import org.example.project.domain.model.Course

fun Course.toDTO(): CourseDTO = CourseDTO(
    courseCode = courseCode,
    courseName = courseName,
    creditHours = creditHours,
    department = department,
    description = description,
    id = id,
    prerequisites = prerequisites
)


fun CourseDTO.toCourse(): Course = Course(
    courseCode = courseCode,
    courseName = courseName,
    creditHours = creditHours,
    department = department,
    description = description,
    id = id,
    prerequisites = prerequisites
)