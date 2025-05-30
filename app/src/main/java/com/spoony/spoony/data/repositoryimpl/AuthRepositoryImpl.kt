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

    override suspend fun signUp(token: String, platform: String, userName: String, birth: String?, regionId: Int?, introduction: String?): Result<TokenEntity> =
        runCatching {
            authRemoteDataSource.signUp(
                token = token,
                platform = platform,
                userName = userName,
                birth = birth,
                regionId = regionId,
                introduction = introduction
            ).data!!.jwtTokenDto!!.toDomain()
        }

    override suspend fun signOut(token: String): Result<Unit> = runCatching {
        authRemoteDataSource.signOut(token).data ?: Unit
    }

    override suspend fun withDraw(token: String): Result<Unit> = runCatching {
        authRemoteDataSource.withDraw(token).data ?: Unit
    }

    override suspend fun refreshToken(token: String): Result<TokenEntity> = runCatching {
        authRemoteDataSource.refreshToken(token).data!!.toDomain()
    }
}
