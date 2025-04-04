package org.example.project.data.remote.repository

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.example.project.data.remote.response.CourseDTO
import org.example.project.data.remote.response.ResponseDTO
import org.example.project.data.remote.response.StudentDTO
import org.example.project.domain.constant.Response
import org.example.project.domain.mapper.toCourse
import org.example.project.domain.mapper.toPagingAndSorting
import org.example.project.domain.mapper.toStudent
import org.example.project.domain.model.Course
import org.example.project.domain.model.PagingAndSort
import org.example.project.domain.model.Student
import org.example.project.domain.repository.Repository

class RepositoryImpl(private val client: HttpClient): Repository {

    private val baseUrl = "http://localhost:8080/v1/students"

    override suspend fun getStudents(
        page: Int, size: Int, sortBy: String, orderBy: String
    ): Response<Pair<PagingAndSort, List<Student>>> {
        return try {
            val response = client.request(
                "$baseUrl/advance?page=$page&size=$size&sortBy=$sortBy&orderType=$orderBy"
            ) { method = HttpMethod.Get }

            if (response.status != HttpStatusCode.OK) {
                return Response.Failure("Student not found!")
            }

            val responseDto = response.body<ResponseDTO>()
            val pagingAndSort = responseDto.pageable.toPagingAndSorting()
            val students = responseDto.content.map { it.toStudent() }

            Response.Success(data = pagingAndSort to students)
        } catch (_: Exception) {
            Response.Failure("Unknown error!")
        }
    }

    override suspend fun getStudent(id: Long): Response<Student> {
        return try {
            val response = client.request("$baseUrl/$id") {
                method = HttpMethod.Get
            }

            if (response.status != HttpStatusCode.OK) {
                return Response.Failure("Student not found!")
            }
            Response.Success(data = response.body<StudentDTO>().toStudent())
        } catch (_: Exception) {
            Response.Failure("Unknown error!")
        }
    }

    override suspend fun getStudentsCourse(id: Long): Response<List<Course>> {
        return try {
            val response = client.request("$baseUrl/$id/courses") {
                method = HttpMethod.Get
            }

            if (response.status != HttpStatusCode.OK) {
                return Response.Failure("Student not found!")
            }
            Response.Success(data = response.body<List<CourseDTO>>().map { it.toCourse() })
        } catch (_: Exception) {
            Response.Failure("Unknown error!")
        }
    }

    override suspend fun createStudent(student: StudentDTO): Response<Student> {
        return try {
            val response = client.request(baseUrl) {
                method = HttpMethod.Post
                headers {
                    append(HttpHeaders.Accept, ContentType.Application.Json.toString())
                    append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                }
                setBody(student)
            }

            if (response.status != HttpStatusCode.OK) {
                return Response.Failure("Failed to create a Student!")
            }
            Response.Success(data = response.body<StudentDTO>().toStudent())
        } catch (_: Exception) {
            Response.Failure("Unknown error!")
        }
    }

    override suspend fun updateStudent(student: StudentDTO): Response<Student> {
        return try {
            val response = client.request("$baseUrl/update") {
                method = HttpMethod.Post
                headers {
                    append(HttpHeaders.Accept, ContentType.Application.Json.toString())
                    append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                }
                setBody(student)
            }

            if (response.status != HttpStatusCode.OK) {
                return Response.Failure("Failed to create a Student!")
            }
                Response.Success(data = response.body<StudentDTO>().toStudent())
        } catch (_: Exception) {
            Response.Failure("Unknown error!")
        }

    }

    override suspend fun registerCourseForStudent(id: Long, courses: List<Course>): Response<Student> {
        return try {
            val response = client.request("$baseUrl/$id/courses") {
                method = HttpMethod.Post
                headers {
                    append(HttpHeaders.Accept, ContentType.Application.Json.toString())
                    append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                }
                setBody(courses)
            }

            if (response.status != HttpStatusCode.OK) {
                return Response.Failure("Failed to register courses to Student!")
            }
            Response.Success(data = response.body<StudentDTO>().toStudent())
        } catch (_: Exception) {

            Response.Failure("Unknown error!")
        }
    }
}