package org.example.project.domain.repository

import org.example.project.data.remote.response.student.StudentDTO
import org.example.project.domain.constant.Response
import org.example.project.domain.model.student.Course
import org.example.project.domain.model.student.PagingAndSort
import org.example.project.domain.model.student.Student

interface StudentRepository {
    suspend fun getStudents(
        page: Int, size: Int, sortBy: String, orderBy: String
    ): Response<Pair<PagingAndSort, List<Student>>>

    suspend fun getStudent(id: Long): Response<Student>

    suspend fun getStudentsCourse(id: Long): Response<List<Course>>

    suspend fun createStudent(student: StudentDTO): Response<Student>

    suspend fun updateStudent(student: StudentDTO): Response<Student>

    suspend fun registerCourseForStudent(id: Long, courses: List<Course>): Response<Student>

}