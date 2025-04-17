package org.example.project.data.remote.repository

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.browser.localStorage
import kotlinx.serialization.Serializable
import org.example.project.data.remote.response.user.*
import org.example.project.domain.constant.AuthResponse
import org.example.project.domain.constant.TokenType
import org.example.project.domain.constant.UserDetailType
import org.example.project.domain.repository.AuthRepository
import org.example.project.domain.repository.AuthTokenRepository

class AuthRepositoryImpl(
    private val client: HttpClient,
    private val authTokenRepository: AuthTokenRepository,
    private val baseUrl: String
): AuthRepository {

    private val authUrl = "$baseUrl/auth"

    override suspend fun register(registrationRequest: RegistrationRequest): AuthResponse {
        return try {
            val response = client.request("$authUrl/register") {
                method = HttpMethod.Post
                headers {
                    append(HttpHeaders.Accept, ContentType.Application.Json.toString())
                    append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                }
                setBody(registrationRequest)
            }

            if (response.status != HttpStatusCode.Created) {
                val result = response.body<BadRequest>()
                return AuthResponse.RegistrationFailure(result.errors)
            }
            AuthResponse.RegistrationSuccessful(response.body<UserDTO>().email)
        } catch (e: Throwable) {
            val message = e.message ?: "Unknown error"
            if (message.contains("Failed to fetch") || message.contains("NetworkError")) {
                AuthResponse.LoginFailed("Cannot connect to server. Please check your network.")
            } else {
                AuthResponse.RegistrationFailure(listOf("Unexpected error"))
            }
        }
    }

    override suspend fun login(loginRequest: LoginRequest): AuthResponse {
        return try {
            val response = client.request("$authUrl/login") {
                method = HttpMethod.Post
                headers {
                    append(HttpHeaders.Accept, ContentType.Application.Json.toString())
                    append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                }
                setBody(loginRequest)
            }

            if (response.status != HttpStatusCode.OK) {
                val result = response.body<BadRequest>()
                return AuthResponse.LoginFailed(result.errors.first())
            }

            val loginDTO = response.body<LoginDTO>()
            authTokenRepository.saveAuthToken(TokenType.ACCESS, loginDTO.accessToken ?: "")
            authTokenRepository.saveAuthToken(TokenType.REFRESH, loginDTO.refreshToken ?: "")
            localStorage.setItem(UserDetailType.FIRSTNAME.key, loginDTO.firstName ?: "")
            localStorage.setItem(UserDetailType.LASTNAME.key, loginDTO.lastName ?: "")
            localStorage.setItem(UserDetailType.EMAIL.key, loginDTO.email ?: "")
            AuthResponse.LoginSuccessful

        } catch (e: Throwable) {
            val message = e.message ?: "Unknown error"
            if (message.contains("Failed to fetch") || message.contains("NetworkError")) {
                AuthResponse.LoginFailed("Cannot connect to server. Please check your network.")
            } else {
                AuthResponse.LoginFailed("Unexpected error")
            }
        }
    }

    override suspend fun refresh(): String? {
        return try {
            val refreshToken = authTokenRepository.getAuthToken(TokenType.REFRESH) ?: return null
            val response = client.request("$authUrl/refresh") {
                method = HttpMethod.Post
                headers {
                    append(HttpHeaders.Accept, ContentType.Application.Json.toString())
                    append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                }
                setBody(RefreshRequest(refreshToken))
            }
            if (response.status != HttpStatusCode.OK) return null
            val loginDTO = response.body<LoginDTO>()
            authTokenRepository.saveAuthToken(TokenType.ACCESS, loginDTO.accessToken ?: "")
            authTokenRepository.saveAuthToken(TokenType.REFRESH, loginDTO.refreshToken ?: "")
            loginDTO.accessToken
        } catch (_: Throwable) { null }
    }

    override suspend fun isTokenValid(): Boolean {
        return try {
            val response = client.request("$baseUrl/verify") { method = HttpMethod.Get }
            if (response.status == HttpStatusCode.Unauthorized) return false
            if (response.status == HttpStatusCode.OK) return true
            false
        } catch (_: Throwable) { false }
    }

    override suspend fun logOut() {
        authTokenRepository.deleteAuthToken(TokenType.ACCESS)
        authTokenRepository.deleteAuthToken(TokenType.REFRESH)
    }

    @Serializable
    data class RefreshRequest(val refreshToken: String)

}