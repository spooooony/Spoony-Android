package com.spoony.spoony.domain.repository

import com.spoony.spoony.domain.entity.TokenEntity
import kotlinx.coroutines.flow.Flow

interface TokenRepository {
    suspend fun getAccessToken(): Flow<String>
    suspend fun getRefreshToken(): Flow<String>
    suspend fun updateTokens(tokens: TokenEntity)
    suspend fun clearTokens()

    suspend fun initCachedAccessToken()
    fun updateCachedAccessToken(token: String)
    fun getCachedAccessToken(): String
}
