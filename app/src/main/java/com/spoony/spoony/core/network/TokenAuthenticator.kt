package com.spoony.spoony.core.network

import com.spoony.spoony.domain.repository.AuthRepository
import com.spoony.spoony.domain.repository.TokenRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val authRepository: AuthRepository
) : Authenticator {
    private val tokenRefreshMutex = Mutex()

    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            handleAuthenticator(response)
        }
    }

    private suspend fun handleAuthenticator(response: Response): Request? {
        return tokenRefreshMutex.withLock {
            val currentAccessToken = tokenRepository.getAccessToken().firstOrNull()
            val requestToken = getRequestToken(response.request)

            // 이미 토큰 재발급이 완료된 경우 -> token 바꿔서 재요청
            if (isTokenRefreshed(requestToken, currentAccessToken)) {
                return@withLock buildRequestWithToken(response.request, currentAccessToken.orEmpty())
            }

            // refreshToken이 없는 경우
            val refreshToken = tokenRepository.getRefreshToken().firstOrNull() ?: return@withLock null

            val newToken = authRepository.refreshToken(refreshToken).getOrNull()

            // 토큰 재발급에 실패한 경우
            if (newToken == null) {
                tokenRepository.clearTokens()
                return@withLock null
            }

            // 토큰 재발급 성공
            tokenRepository.updateTokens(newToken)
            return buildRequestWithToken(response.request, newToken.accessToken)
        }
    }

    private fun getRequestToken(request: Request): String? =
        request.header(AUTHORIZATION)?.removePrefix("$BEARER ")

    private fun isTokenRefreshed(requestToken: String?, currentAccessToken: String?): Boolean {
        if (requestToken == null || currentAccessToken == null) return false

        return requestToken != currentAccessToken
    }

    private fun buildRequestWithToken(request: Request, token: String): Request =
        request.newBuilder()
            .removeHeader(AUTHORIZATION)
            .addHeader(AUTHORIZATION, token)
            .build()

    companion object {
        private const val AUTHORIZATION = "Authorization"
        private const val BEARER = "Bearer"
    }
}
