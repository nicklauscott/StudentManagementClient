package org.example.project.domain.model

data class Course(
    val courseCode: String,
    val courseName: String,
    val creditHours: Int,
    val department: String,
    val description: String,
    val id: Int,
    val prerequisites: List<String>
)