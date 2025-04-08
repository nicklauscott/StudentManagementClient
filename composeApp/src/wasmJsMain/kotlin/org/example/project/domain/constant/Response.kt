package org.example.project.domain.constant

interface Response<T> {
    data class Success<T>(val data: T, val message: String? = null): Response<T>
    data class Failure<T>(val messages: List<String>): Response<T>

}

interface AuthResponse {
    data class RegistrationFailure(val messages: List<String>): AuthResponse
    data object RegistrationSuccessful: AuthResponse
    data object LoginSuccessful: AuthResponse
    data object LoginFailed: AuthResponse
}