package com.spoony.spoony.core.network

import com.spoony.spoony.domain.repository.AuthRepository
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
    private val authRepository: AuthRepository
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            val refreshToken = tokenRepository.getRefreshToken().firstOrNull() ?: return@runBlocking null

            val result = authRepository.refreshToken(refreshToken)
            val newToken = result.getOrNull()

            if (newToken == null) {
                tokenRepository.clearTokens()
                return@runBlocking null
            }

            tokenRepository.updateTokens(newToken)

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
