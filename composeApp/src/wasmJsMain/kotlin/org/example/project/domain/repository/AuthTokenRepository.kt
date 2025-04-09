package org.example.project.domain.repository

import org.example.project.domain.constant.TokenType

interface AuthTokenRepository {

     fun saveAuthToken(type: TokenType, token: String)
     fun getAuthToken(type: TokenType): String?
     fun deleteAuthToken(type: TokenType)

}