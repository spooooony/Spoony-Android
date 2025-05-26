package com.spoony.spoony.domain.repository

import com.spoony.spoony.domain.entity.BasicUserInfoEntity

interface UserRepository {
    suspend fun getMyInfo(): Result<BasicUserInfoEntity>

    suspend fun getUserInfoById(userId: Int): Result<BasicUserInfoEntity>
}
