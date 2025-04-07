package org.example.project.data.remote.response.student

import kotlinx.serialization.Serializable

@Serializable
data class CourseDTO(
    val courseCode: String,
    val courseName: String,
    val creditHours: Int,
    val department: String,
    val description: String,
    val id: Int,
    val prerequisites: List<String>
)