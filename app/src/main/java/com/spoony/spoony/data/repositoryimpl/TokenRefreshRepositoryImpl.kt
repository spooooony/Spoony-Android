package com.spoony.spoony.data.repositoryimpl

import com.spoony.spoony.data.datasource.TokenRefreshDataSource
import com.spoony.spoony.data.mapper.toDomain
import com.spoony.spoony.domain.entity.TokenEntity
import com.spoony.spoony.domain.repository.TokenRefreshRepository
import javax.inject.Inject

class TokenRefreshRepositoryImpl @Inject constructor(
    private val tokenRefreshDataSource: TokenRefreshDataSource
) : TokenRefreshRepository {
    override suspend fun refreshToken(token: String): Result<TokenEntity> = runCatching {
        tokenRefreshDataSource.refreshToken(token).data!!.toDomain()
    }
}
