package org.example.project.domain.mapper.student

import org.example.project.data.remote.response.student.StudentDTO
import org.example.project.domain.model.student.Gender
import org.example.project.domain.model.student.Student
import org.example.project.domain.model.student.StudentStatus

fun Student.toDTO(): StudentDTO = StudentDTO(
    address = address,
    course = course.map { it.toDTO() },
    dateOfBirth = dateOfBirth,
    department = department,
    email = email,
    enrollmentDate = enrollmentDate,
    firstName = firstName,
    gender = gender.toString(),
    guardianMobile = guardianMobile,
    id = id,
    lastName = lastName,
    program = program,
    status = status.toString()
)


fun StudentDTO.toStudent(): Student = Student(
    address = address,
    course = course.map { it.toCourse() },
    dateOfBirth = dateOfBirth,
    department = department,
    email = email,
    enrollmentDate = enrollmentDate,
    firstName = firstName,
    gender = Gender.valueOf(gender),
    guardianMobile = guardianMobile,
    id = id,
    lastName = lastName,
    program = program,
    status = StudentStatus.valueOf(status)
)