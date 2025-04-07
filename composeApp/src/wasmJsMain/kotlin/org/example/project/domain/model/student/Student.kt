package org.example.project.domain.model.student

import kotlinx.datetime.LocalDateTime


data class Student(
    val address: String = "",
    val course: List<Course> = emptyList(),
    val dateOfBirth: LocalDateTime = LocalDateTime.parse("1995-01-01T08:00:00"),
    val department: String = "Chem Lab",
    val email: String = "",
    val enrollmentDate: LocalDateTime = LocalDateTime.parse("1995-01-01T08:00:00"),
    val firstName: String = "Mark",
    val gender: Gender = Gender.MALE,
    val guardianMobile: String = "",
    val id: Int = (1..10).random() * (1..10).random() / 2,
    val lastName: String = "Johnson",
    val program: String = "",
    val status: StudentStatus = StudentStatus.ACTIVE
)

enum class Gender {
    MALE, FEMALE, OTHER;
}

enum class StudentStatus {
    ACTIVE, GRADUATED, SUSPENDED, DROPPED_OUT;
}

enum class Department(val programs: List<String>) {
    Physical_Education(listOf( "Health Education", "Sports Management", "Kinesiology", "Team Sports", "Individual Sports")),
    Chemistry(listOf("Bio Chem")),
    Fine_Arts(listOf( "Painting", "Sculpture", "Music", "Theater", "Dance")),
    Science(listOf("Physics", "Chemistry", "Biology", "Astronomy", "Environmental Science")),
    History(listOf("World History", "Modern History", "Ancient Civilizations", "American History", "European History")),
    Mathematics(listOf("Algebra", "Calculus", "Geometry","Statistics", "Probability")),
    Computer_Science(listOf("Programming", "Data Structures", "Algorithms", "Artificial Intelligence", "Cybersecurity")),
    Literature(listOf("English Literature", "World Literature", "Poetry", "Shakespearean Literature","Modern Literature")),
    Social_Studies(listOf( "Geography", "Civics", "Economics", "Psychology", "Sociology")),
    None(emptyList());
}

fun toDepartment(string: String): Department {
    return try {
        Department.valueOf(string.replace(" ", "_"))
    } catch (_: Exception) {
        Department.None
    }
}


