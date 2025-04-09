package org.example.project.data.remote.response.user

import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    val email: String,
    val firstName: String,
    val lastName: String,
)

