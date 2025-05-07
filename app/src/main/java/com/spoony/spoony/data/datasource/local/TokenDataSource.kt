package com.spoony.spoony.data.datasource.local

import kotlinx.coroutines.flow.Flow

interface TokenDataSource {
    suspend fun getAccessToken(): Flow<String>
    suspend fun getRefreshToken(): Flow<String>
    suspend fun updateAccessToken(accessToken: String)
    suspend fun updateRefreshToken(refreshToken: String)
    suspend fun clearTokens()
}
