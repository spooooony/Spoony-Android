package com.spoony.spoony.domain.repository

import com.spoony.spoony.domain.entity.UserEntity

interface AuthRepository {
    suspend fun getUserInfo(): Result<UserEntity>
    suspend fun getUserInfoById(userId: Int): Result<UserEntity>
    suspend fun getSpoonCount(): Result<Int>
}
