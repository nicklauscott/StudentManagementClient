package org.example.project.data.remote.repository

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.example.project.data.remote.response.student.CourseDTO
import org.example.project.data.remote.response.student.ResponseDTO
import org.example.project.data.remote.response.student.StudentDTO
import org.example.project.data.remote.response.student.StudentFailureResponse
import org.example.project.domain.constant.Response
import org.example.project.domain.mapper.student.toCourse
import org.example.project.domain.mapper.student.toPagingAndSorting
import org.example.project.domain.mapper.student.toStudent
import org.example.project.domain.model.student.Course
import org.example.project.domain.model.student.PagingAndSort
import org.example.project.domain.model.student.Student
import org.example.project.domain.repository.StudentRepository

class StudentRepositoryImpl(private val client: HttpClient): StudentRepository {

    private val baseUrl = "http://localhost:8080/v1/students"

    override suspend fun getStudents(
        page: Int, size: Int, sortBy: String, orderBy: String
    ): Response<Pair<PagingAndSort, List<Student>>> {
        return try {
            val response = client.request(
                "$baseUrl/advance?page=$page&size=$size&sortBy=$sortBy&orderType=$orderBy"
            ) { method = HttpMethod.Get }

            if (response.status != HttpStatusCode.OK) {
                return Response.Failure(listOf("Student not found!"))
            }

            val responseDto = response.body<ResponseDTO>()
            val pagingAndSort = responseDto.pageable.toPagingAndSorting()
            val students = responseDto.content.map { it.toStudent() }

            Response.Success(data = pagingAndSort to students)
        } catch (ex: Exception) {
            Response.Failure(listOf("Unknown error!"))
        }
    }

    override suspend fun getStudent(id: Long): Response<Student> {
        return try {
            val response = client.request("$baseUrl/$id") { method = HttpMethod.Get }

            if (response.status == HttpStatusCode.NotFound) {
                val error = response.body<StudentFailureResponse>()
                return Response.Failure(error.errors)
            }
            Response.Success(data = response.body<StudentDTO>().toStudent())
        } catch (_: Exception) {
            Response.Failure(listOf("Unknown error!"))
        }
    }

    override suspend fun getStudentsCourse(id: Long): Response<List<Course>> {
        return try {
            val response = client.request("$baseUrl/$id/courses") { method = HttpMethod.Get }

            if (response.status == HttpStatusCode.NotFound) {
                val error = response.body<StudentFailureResponse>()
                return Response.Failure(error.errors)
            }
            Response.Success(data = response.body<List<CourseDTO>>().map { it.toCourse() })
        } catch (_: Exception) {
            Response.Failure(listOf("Unknown error!"))
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

            if (response.status == HttpStatusCode.BadRequest) {
                val failedResponse = response.body<StudentFailureResponse>()
                return Response.Failure(failedResponse.errors)
            }

            if (response.status != HttpStatusCode.OK) {
                return Response.Failure(listOf("Failed to create a Student!"))
            }
            Response.Success(data = response.body<StudentDTO>().toStudent())
        } catch (_: Exception) {
            Response.Failure(listOf("Unknown error!"))
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

            if (response.status == HttpStatusCode.BadRequest) {
                val failedResponse = response.body<StudentFailureResponse>()
                return Response.Failure(failedResponse.errors)
            }

            if (response.status != HttpStatusCode.OK) {
                return Response.Failure(listOf("Failed to create a Student!"))
            }
                Response.Success(data = response.body<StudentDTO>().toStudent())
        } catch (ex: Exception) {
            Response.Failure(listOf("Unknown error!"))
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
                return Response.Failure(listOf("Failed to register courses to Student!"))
            }
            Response.Success(data = response.body<StudentDTO>().toStudent())
        } catch (_: Exception) {

            Response.Failure(listOf("Unknown error!"))
        }
    }
}