package org.example.project.domain.constant

enum class TokenType(val key: String) {
    ACCESS("user_access_token"), REFRESH("user_refresh_token")
}

enum class UserDetailType(val key: String) {
    FIRSTNAME("user_first_name"),
    LASTNAME("user_last_name"),
    EMAIL("user_email"),
}