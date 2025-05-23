package com.spoony.spoony.data.repositoryimpl

import com.spoony.spoony.data.datasource.local.TokenDataSource
import com.spoony.spoony.domain.repository.TokenRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class TokenRepositoryImpl @Inject constructor(
    private val tokenDataSource: TokenDataSource
) : TokenRepository {
    private var cachedAccessToken = ""

    override suspend fun getAccessToken(): Flow<String> = tokenDataSource.getAccessToken()

    override suspend fun getRefreshToken(): Flow<String> = tokenDataSource.getRefreshToken()

    override suspend fun updateAccessToken(accessToken: String) {
        cachedAccessToken = accessToken
        tokenDataSource.updateAccessToken(accessToken)
    }

    override suspend fun updateRefreshToken(refreshToken: String) = tokenDataSource.updateRefreshToken(refreshToken)

    override suspend fun clearTokens() = tokenDataSource.clearTokens()

    override suspend fun initCachedAccessToken() {
        cachedAccessToken = getAccessToken().firstOrNull().orEmpty()
    }

    override fun getCachedAccessToken(): String = cachedAccessToken
}
