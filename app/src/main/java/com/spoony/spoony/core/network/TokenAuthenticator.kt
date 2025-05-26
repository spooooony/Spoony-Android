package com.spoony.spoony.core.network

import com.spoony.spoony.domain.repository.TokenRefreshRepository
import com.spoony.spoony.domain.repository.TokenRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val tokenRefreshRepository: TokenRefreshRepository
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            val refreshToken = tokenRepository.getRefreshToken().firstOrNull() ?: return@runBlocking null

            val result = tokenRefreshRepository.refreshToken(refreshToken)
            val newToken = result.getOrNull()

            if (newToken == null) {
                tokenRepository.clearTokens()
                return@runBlocking null
            }

            tokenRepository.updateCachedAccessToken(newToken.accessToken)
            tokenRepository.updateAccessToken(newToken.accessToken)
            tokenRepository.updateRefreshToken(newToken.refreshToken)

            response.request.newBuilder()
                .removeHeader(AUTHORIZATION)
                .addHeader(AUTHORIZATION, "$BEARER ${newToken.accessToken}")
                .build()
        }
    }

    companion object {
        private const val AUTHORIZATION = "Authorization"
        private const val BEARER = "Bearer"
    }
}
