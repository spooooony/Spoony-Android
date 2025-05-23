package com.spoony.spoony.domain.repository

import com.spoony.spoony.domain.entity.TokenEntity
import com.spoony.spoony.domain.entity.UserEntity

interface AuthRepository {
    suspend fun getUserInfo(): Result<UserEntity>
    suspend fun login(): Result<TokenEntity?>
}
