package org.example.project.domain.mapper.student

import org.example.project.data.remote.response.student.CourseDTO
import org.example.project.domain.model.student.Course

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