package org.example.project.data.remote.response.user

import kotlinx.serialization.Serializable

@Serializable
data class BadRequest(
    val email: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val password: String? = null,
    val error_message: String? = null
) {
    val errors: List<String>
        get() = listOfNotNull(
            email?.replaceFirstChar { it.uppercase() },
            firstName?.replaceFirstChar { it.uppercase() },
            lastName?.replaceFirstChar { it.uppercase() },
            password?.replaceFirstChar { it.uppercase() },
            error_message?.replaceFirstChar { it.uppercase() }
        )
}