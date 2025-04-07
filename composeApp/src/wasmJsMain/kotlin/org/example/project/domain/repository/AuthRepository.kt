package org.example.project.domain.repository

import org.example.project.data.remote.response.user.LoginRequest
import org.example.project.data.remote.response.user.RegistrationRequest
import org.example.project.domain.constant.AuthResponse

interface AuthRepository {

    suspend fun register(registrationRequest: RegistrationRequest): AuthResponse

    suspend fun login(loginRequest: LoginRequest): AuthResponse

    suspend fun refresh(): String?

}