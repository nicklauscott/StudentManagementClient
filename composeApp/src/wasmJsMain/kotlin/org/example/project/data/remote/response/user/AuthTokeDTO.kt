package org.example.project.data.remote.response.user

import kotlinx.serialization.Serializable

@Serializable
data class AuthTokeDTO(
    val accessToken: String,
    val refreshToken: String
)