package com.spoony.spoony.core.network

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.jakewharton.processphoenix.ProcessPhoenix
import com.spoony.spoony.domain.repository.TokenRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AuthenticatorErrorHandlerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val tokenRepository: TokenRepository
) : AuthenticatorErrorHandler {
    private val handler by lazy { Handler(Looper.getMainLooper()) }

    override suspend fun handleTokenNullError() {
        tokenRepository.clearTokens()
        restartApp(RETRY_SIGN_IN)
    }

    override suspend fun handleTokenReissueError() {
        tokenRepository.clearTokens()
        restartApp(RETRY_SIGN_IN)
    }

    private fun restartApp(message: String) {
        handler.post {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            ProcessPhoenix.triggerRebirth(context)
        }
    }

    private companion object {
        const val RETRY_SIGN_IN = "재로그인이 필요합니다"
    }
}
