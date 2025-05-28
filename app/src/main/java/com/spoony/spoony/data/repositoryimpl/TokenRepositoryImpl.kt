package com.spoony.spoony.data.repositoryimpl

import com.spoony.spoony.data.datasource.local.TokenDataSource
import com.spoony.spoony.domain.entity.TokenEntity
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

    override suspend fun updateTokens(tokens: TokenEntity) {
        cachedAccessToken = tokens.accessToken
        tokenDataSource.updateAccessToken(tokens.accessToken)
        tokenDataSource.updateRefreshToken(tokens.refreshToken)
    }

    override suspend fun clearTokens() {
        cachedAccessToken = ""
        tokenDataSource.clearTokens()
    }

    override suspend fun initCachedAccessToken() {
        cachedAccessToken = tokenDataSource.getAccessToken().firstOrNull().orEmpty()
    }

    override fun updateCachedAccessToken(token: String) {
        cachedAccessToken = token
    }

    override fun getCachedAccessToken(): String = cachedAccessToken
}
