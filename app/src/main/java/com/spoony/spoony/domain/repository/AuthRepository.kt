package com.spoony.spoony.domain.repository

import com.spoony.spoony.domain.entity.TokenEntity

interface AuthRepository {
    suspend fun signIn(
        token: String,
        platform: String
    ): Result<TokenEntity?>
}
