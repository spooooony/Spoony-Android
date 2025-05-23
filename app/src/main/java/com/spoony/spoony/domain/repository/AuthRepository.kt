package com.spoony.spoony.domain.repository

import com.spoony.spoony.domain.entity.TokenEntity

interface AuthRepository {
    suspend fun signIn(
        token: String,
        platform: String
    ): Result<TokenEntity?>

    suspend fun signUp(
        token: String,
        platform: String,
        userName: String,
        birth: String?,
        regionId: Int?,
        introduction: String?
    ): Result<TokenEntity>
}
