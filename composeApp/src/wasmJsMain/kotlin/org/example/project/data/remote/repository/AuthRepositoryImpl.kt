package org.example.project.data.remote.repository

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.Serializable
import org.example.project.data.remote.response.user.*
import org.example.project.domain.constant.AuthResponse
import org.example.project.domain.constant.TokenType
import org.example.project.domain.repository.AuthRepository
import org.example.project.domain.repository.AuthTokenRepository

class AuthRepositoryImpl(
    private val client: HttpClient, private val authTokenRepository: AuthTokenRepository
): AuthRepository {

    private val baseUrl = "http://localhost:8080/v1/auth"

    override suspend fun register(registrationRequest: RegistrationRequest): AuthResponse {
        return try {
            val response = client.request("$baseUrl/register") {
                method = HttpMethod.Post
                headers {
                    append(HttpHeaders.Accept, ContentType.Application.Json.toString())
                    append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                }
                setBody(registrationRequest)
            }

            if (response.status != HttpStatusCode.Created || response.status != HttpStatusCode.Conflict) {
                val result = response.body<BadRequest>()
                return AuthResponse.RegistrationFailure(result.errors)
            }
            AuthResponse.LoginSuccessful
        } catch (_: Exception) {
            AuthResponse.RegistrationFailure(listOf("Unknown error!"))
        }
    }

    override suspend fun login(loginRequest: LoginRequest): AuthResponse {
        return try {
            val response = client.request("$baseUrl/register") {
                method = HttpMethod.Post
                headers {
                    append(HttpHeaders.Accept, ContentType.Application.Json.toString())
                    append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                }
                setBody(loginRequest)
            }

            if (response.status != HttpStatusCode.BadRequest) {
                val result = response.body<BadRequest>()
                return AuthResponse.RegistrationFailure(result.errors)
            }

            val authTokeDTO = response.body<AuthTokeDTO>()
            authTokenRepository.saveAuthToken(TokenType.ACCESS, authTokeDTO.accessToken)
            authTokenRepository.saveAuthToken(TokenType.REFRESH, authTokeDTO.refreshToken)



            AuthResponse.LoginSuccessful
        } catch (_: Exception) {
            AuthResponse.RegistrationFailure(listOf("Unknown error!"))
        }
    }

    override suspend fun refresh(): String? {
        return try {
            val refreshToken = authTokenRepository.getAuthToken(TokenType.REFRESH)
                ?: return null

            val response = client.request("$baseUrl/refresh") {
                method = HttpMethod.Post
                headers {
                    append(HttpHeaders.Accept, ContentType.Application.Json.toString())
                    append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                }
                setBody(RefreshRequest(refreshToken))
            }

            if (response.status != HttpStatusCode.OK) return null

            val authTokeDTO = response.body<AuthTokeDTO>()
            authTokenRepository.saveAuthToken(TokenType.ACCESS, authTokeDTO.accessToken)
            authTokenRepository.saveAuthToken(TokenType.REFRESH, authTokeDTO.refreshToken)

            authTokeDTO.accessToken
        } catch (_: Exception) {
            null
        }
    }

    @Serializable
    data class RefreshRequest(val refreshToken: String)
}