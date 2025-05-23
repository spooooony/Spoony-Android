package com.spoony.spoony.domain.repository

import com.spoony.spoony.domain.entity.RegionEntity
import com.spoony.spoony.domain.entity.UserEntity

interface UserRepository {
    suspend fun getUserInfoById(userId: Int): Result<UserEntity>
    suspend fun getRegionList(): Result<List<RegionEntity>>
    suspend fun checkUserNameExist(userName: String): Result<Boolean>
}
