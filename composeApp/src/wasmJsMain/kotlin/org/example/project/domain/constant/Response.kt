package org.example.project.domain.constant

interface Response<T> {
    data class Success<T>(val data: T, val message: String? = null): Response<T>
    data class Failure<T>(val message: String? = null): Response<T>
}