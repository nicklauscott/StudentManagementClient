package org.example.project.data.remote.response.user

import kotlinx.serialization.Serializable

@Serializable
data class BadRequest(
    val email: String?,
    val firstName: String?,
    val lastName: String?,
    val password: String?,
    val error_message: String?
) {
    val errors: List<String>
        get() = listOfNotNull(email, firstName, lastName, password, error_message)
}