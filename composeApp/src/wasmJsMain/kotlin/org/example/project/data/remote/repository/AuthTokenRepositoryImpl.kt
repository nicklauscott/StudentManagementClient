package org.example.project.data.remote.repository

import kotlinx.browser.localStorage
import org.example.project.domain.constant.TokenType
import org.example.project.domain.repository.AuthTokenRepository

class AuthTokenRepositoryImpl: AuthTokenRepository {
    override fun saveAuthToken(type: TokenType, token: String) {
        deleteAuthToken(type)
        localStorage.setItem(type.key, token)
    }

    override fun getAuthToken(type: TokenType): String? {
        return localStorage.getItem(type.key)
    }

    override fun deleteAuthToken(type: TokenType) {
        localStorage.removeItem(type.key)
    }
}