package com.spoony.spoony.data.repositoryimpl

import com.spoony.spoony.data.datasource.UserRemoteDataSource
import com.spoony.spoony.data.mapper.toDomain
import com.spoony.spoony.domain.entity.RegionEntity
import com.spoony.spoony.domain.entity.UserEntity
import com.spoony.spoony.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {
    override suspend fun getUserInfoById(userId: Int): Result<UserEntity> =
        runCatching {
            userRemoteDataSource.getUserInfoById(userId).data!!.toDomain()
        }

    override suspend fun getRegionList(): Result<List<RegionEntity>> =
        runCatching {
            userRemoteDataSource.getRegionList().data!!.regionList.map { it.toDomain() }
        }
}
