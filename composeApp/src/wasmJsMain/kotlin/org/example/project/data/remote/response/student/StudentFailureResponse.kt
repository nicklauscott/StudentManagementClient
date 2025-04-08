package org.example.project.data.remote.response.student

import kotlinx.serialization.Serializable

@Serializable
data class StudentFailureResponse(
    val email: String? = null,
    val firstName: String? = null,
    val guardianMobile: String? = null,
    val lastName: String? = null,
    val error_message: String? = null
) {
    val errors: List<String>
        get() = listOfNotNull(
            email?.replaceFirstChar { it.uppercase() },
            firstName?.replaceFirstChar { it.uppercase() },
            lastName?.replaceFirstChar { it.uppercase() },
            guardianMobile?.replaceFirstChar { it.uppercase() },
            error_message?.replaceFirstChar { it.uppercase() },
        )
}