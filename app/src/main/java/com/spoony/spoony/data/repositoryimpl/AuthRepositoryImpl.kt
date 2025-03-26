package com.spoony.spoony.data.repositoryimpl

import com.spoony.spoony.data.datasource.AuthRemoteDataSource
import com.spoony.spoony.data.mapper.toDomain
import com.spoony.spoony.domain.entity.UserEntity
import com.spoony.spoony.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {
    override suspend fun getUserInfo(): Result<UserEntity> =
        runCatching {
            authRemoteDataSource.getUserInfo().data!!.toDomain()
        }

    override suspend fun getSpoonCount(): Result<Int> =
        runCatching {
            authRemoteDataSource.getSpoonCount().data!!.spoonAmount
        }
}
