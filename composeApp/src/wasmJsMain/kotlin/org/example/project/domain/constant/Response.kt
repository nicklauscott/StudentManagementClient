package org.example.project.domain.constant

interface Response<T> {
    data class Success<T>(val data: T, val message: String? = null): Response<T>
    data class Failure<T>(val messages: List<String>): Response<T>

}

interface AuthResponse {
    data class RegistrationFailure(val messages: List<String>): AuthResponse
    data class RegistrationSuccessful(val email: String): AuthResponse
    data object LoginSuccessful: AuthResponse
    data class LoginFailed(val messages: String): AuthResponse
}