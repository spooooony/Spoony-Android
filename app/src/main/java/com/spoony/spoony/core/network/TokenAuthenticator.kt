package com.spoony.spoony.core.network

import android.content.Context
import android.widget.Toast
import com.spoony.spoony.domain.repository.AuthRepository
import com.spoony.spoony.domain.repository.TokenRepository
import dagger.hilt.android.qualifiers.ApplicationContext
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
    private val authRepository: AuthRepository,
    @ApplicationContext private val context: Context
) : Authenticator {
    private val tokenRefreshMutex = Mutex()

    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            tokenRefreshMutex.withLock {
                val currentAccessToken = tokenRepository.getAccessToken().firstOrNull()

                val requestToken = response.request.header(AUTHORIZATION)?.removePrefix("$BEARER ")
                if (currentAccessToken != null && requestToken != currentAccessToken) {
                    // 이미 토큰이 갱신된 경우 다시 시도
                    return@runBlocking response.request.newBuilder()
                        .removeHeader(AUTHORIZATION)
                        .addHeader(AUTHORIZATION, "$BEARER $currentAccessToken")
                        .build()
                }

                val refreshToken = tokenRepository.getRefreshToken().firstOrNull() ?: return@runBlocking null

                val result = authRepository.refreshToken(refreshToken)
                val newToken = result.getOrNull()

                if (newToken == null) {
                    tokenRepository.clearTokens()
                    Toast.makeText(context, "재로그인이 필요합니다", Toast.LENGTH_SHORT).show()
                    return@runBlocking null
                }

                tokenRepository.updateTokens(newToken)

                response.request.newBuilder()
                    .removeHeader(AUTHORIZATION)
                    .addHeader(AUTHORIZATION, "$BEARER ${newToken.accessToken}")
                    .build()
            }
        }
    }

    companion object {
        private const val AUTHORIZATION = "Authorization"
        private const val BEARER = "Bearer"
    }
}
