package org.example.project.data.remote.response.user

import kotlinx.serialization.Serializable

@Serializable
data class LoginDTO(
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val accessToken: String? = null,
    val refreshToken: String? = null
)

