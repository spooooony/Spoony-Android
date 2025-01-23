package com.spoony.spoony.domain.repository

import com.spoony.spoony.domain.entity.UserEntity

interface AuthRepository {
    suspend fun getUserInfo(userId: Int): Result<UserEntity>
}
