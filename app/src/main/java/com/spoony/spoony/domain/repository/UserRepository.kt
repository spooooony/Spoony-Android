package com.spoony.spoony.domain.repository

import com.spoony.spoony.domain.entity.UserEntity

interface UserRepository {
    suspend fun getUserInfoById(userId: Int): Result<UserEntity>
}
