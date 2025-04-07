package org.example.project.data.remote.response.student

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import org.example.project.domain.constant.LocalDateTimeSerializer

@Serializable
data class StudentDTO(
    val address: String,
    val course: List<CourseDTO>,

    @Serializable(with = LocalDateTimeSerializer::class)
    val dateOfBirth: LocalDateTime,

    val department: String,
    val email: String,

    @Serializable(with = LocalDateTimeSerializer::class)
    val enrollmentDate: LocalDateTime,

    val firstName: String,
    val gender: String,
    val guardianMobile: String,
    val id: Int,
    val lastName: String,
    val program: String,
    val status: String
)


