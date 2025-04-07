package org.example.project.di

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.js.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.browser.localStorage
import kotlinx.serialization.json.Json
import org.example.project.data.remote.repository.AuthRepositoryImpl
import org.example.project.data.remote.repository.AuthRepositoryImpl.RefreshRequest
import org.example.project.data.remote.repository.AuthTokenRepositoryImpl
import org.example.project.data.remote.repository.StudentRepositoryImpl
import org.example.project.data.remote.response.user.AuthTokeDTO
import org.example.project.data.remote.response.user.BadRequest
import org.example.project.domain.constant.AuthResponse
import org.example.project.domain.constant.TokenType
import org.example.project.domain.repository.AuthRepository
import org.example.project.domain.repository.AuthTokenRepository
import org.example.project.domain.repository.StudentRepository
import org.example.project.presentation.screens.home.HomeScreenManager
import org.koin.dsl.module
import kotlin.text.get

lateinit var setBearerTokens: (BearerTokens?) -> Unit

val module = module {

    val baseUrl = "http://localhost:8080/v1/auth"

    factory<AuthTokenRepository> { AuthTokenRepositoryImpl() }

    single {
        val authTokenRepository: AuthTokenRepository = get()
        val refreshToken = localStorage.getItem(TokenType.REFRESH.key) ?: ""
        HttpClient(Js) {
            install(Auth) {
                /*
                bearer {
                    refreshTokens {
                        val response = client.request("$baseUrl/refresh") {
                            method = HttpMethod.Post
                            headers {
                                append(HttpHeaders.Accept, ContentType.Application.Json.toString())
                                append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                            }
                            setBody(RefreshRequest(refreshToken))
                        }

                        var bearerTokens: BearerTokens? = null
                        if (response.status == HttpStatusCode.OK) {
                            val result = response.body<AuthTokeDTO>()
                            authTokenRepository.saveAuthToken(TokenType.ACCESS, result.accessToken)
                            authTokenRepository.saveAuthToken(TokenType.REFRESH, result.refreshToken)
                            bearerTokens = BearerTokens(accessToken = result.accessToken, refreshToken = result.refreshToken)
                        }
                        bearerTokens
                    }
                }
                 */
            }

            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
    }

    /*
    single {

        val authTokenRepository: AuthTokenRepository = get()

        HttpClient(Js) {
            install(Auth) {
                bearer {
                    loadTokens {
                        BearerTokens(
                            authTokenRepository.getAuthToken(TokenType.ACCESS) ?: "",
                            authTokenRepository.getAuthToken(TokenType.REFRESH) ?: ""
                        )
                    }
                    /*
                    refreshTokens {
                        val response = client.request("$baseUrl/refresh") {
                            method = HttpMethod.Post
                            headers {
                                append(HttpHeaders.Accept, ContentType.Application.Json.toString())
                                append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                            }
                            setBody(RefreshRequest(authTokenRepository.getAuthToken(TokenType.REFRESH) ?: ""))
                        }.body<AuthTokeDTO>()
                        BearerTokens(accessToken = response.accessToken, refreshToken = response.refreshToken)
                    }

                     */
                }
            }

            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
    }

     */

    factory<StudentRepository> { StudentRepositoryImpl(get(), get(), get()) }

    factory<AuthRepository> { AuthRepositoryImpl(get(), get()) }

    single { HomeScreenManager(get()) }

}