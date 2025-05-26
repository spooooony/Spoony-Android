package com.spoony.spoony.domain.repository

import com.spoony.spoony.domain.entity.BasicUserInfoEntity
import com.spoony.spoony.domain.entity.RegionEntity

interface UserRepository {
    suspend fun getMyInfo(): Result<BasicUserInfoEntity>

    suspend fun getUserInfoById(userId: Int): Result<BasicUserInfoEntity>
    suspend fun getRegionList(): Result<List<RegionEntity>>
    suspend fun checkUserNameExist(userName: String): Result<Boolean>
}