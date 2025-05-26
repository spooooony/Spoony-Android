package com.spoony.spoony.domain.repository

import com.spoony.spoony.domain.entity.TokenEntity

interface TokenRefreshRepository {
    suspend fun refreshToken(token: String): Result<TokenEntity>
}
