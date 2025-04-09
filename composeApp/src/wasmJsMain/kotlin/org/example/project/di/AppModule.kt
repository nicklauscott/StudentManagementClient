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
import kotlinx.serialization.json.Json
import org.example.project.data.remote.repository.AuthRepositoryImpl
import org.example.project.data.remote.repository.AuthRepositoryImpl.RefreshRequest
import org.example.project.data.remote.repository.AuthTokenRepositoryImpl
import org.example.project.data.remote.repository.StudentRepositoryImpl
import org.example.project.data.remote.response.user.LoginDTO
import org.example.project.domain.constant.TokenType
import org.example.project.domain.repository.AuthRepository
import org.example.project.domain.repository.AuthTokenRepository
import org.example.project.domain.repository.StudentRepository
import org.example.project.presentation.screens.home.HomeScreenManager
import org.example.project.presentation.screens.index.IndexScreenManager
import org.example.project.presentation.screens.login.SignInAndSignUpScreenManager
import org.example.project.presentation.screens.sidepanel.SidePanelScreenManager
import org.koin.dsl.module


val module = module {

    factory<String> { "http://localhost:8080/v1" }

    factory<AuthTokenRepository> { AuthTokenRepositoryImpl() }

    single {
        val baseUrl: String = get()
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
                    refreshTokens {
                        try {
                            val response = client.request("$baseUrl/auth/refresh") {
                                method = HttpMethod.Post
                                headers {
                                    append(HttpHeaders.Accept, ContentType.Application.Json.toString())
                                    append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                                }
                                setBody(RefreshRequest(authTokenRepository.getAuthToken(TokenType.REFRESH) ?: ""))
                            }.body<LoginDTO>()
                            authTokenRepository.saveAuthToken(TokenType.ACCESS, response.accessToken ?: "")
                            authTokenRepository.saveAuthToken(TokenType.REFRESH, response.refreshToken ?: "")
                            BearerTokens(accessToken = response.accessToken ?: "", refreshToken = response.refreshToken)
                        } catch (_: Exception) { null }
                    }
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

    factory<StudentRepository> { StudentRepositoryImpl(get()) }

    factory<AuthRepository> { AuthRepositoryImpl(get(), get(), get()) }

    factory { HomeScreenManager(get()) }

    factory { IndexScreenManager(get()) }

    factory { SignInAndSignUpScreenManager(get()) }

    factory { SidePanelScreenManager(get()) }

}