package com.spoony.spoony.domain.repository

import kotlinx.coroutines.flow.Flow

interface TokenRepository {
    suspend fun getAccessToken(): Flow<String>
    suspend fun getRefreshToken(): Flow<String>
    suspend fun updateAccessToken(accessToken: String)
    suspend fun updateRefreshToken(refreshToken: String)
    suspend fun clearTokens()
}
