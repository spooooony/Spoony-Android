package com.spoony.spoony.data.repositoryimpl

import com.spoony.spoony.data.datasource.AuthRemoteDataSource
import com.spoony.spoony.data.mapper.toDomain
import com.spoony.spoony.domain.entity.TokenEntity
import com.spoony.spoony.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {
    override suspend fun signIn(
        token: String,
        platform: String
    ): Result<TokenEntity?> =
        runCatching {
            authRemoteDataSource.signIn(
                token = token,
                platform = platform
            ).data!!.jwtTokenDto?.toDomain()
        }
}
