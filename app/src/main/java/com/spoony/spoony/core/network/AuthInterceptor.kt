package com.spoony.spoony.core.network

import com.spoony.spoony.domain.repository.TokenRepository
import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber

class AuthInterceptor @Inject constructor(
    private val tokenRepository: TokenRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        Timber.d("ACCESS_TOKEN: ${tokenRepository.getCachedAccessToken()}")

        val originalRequest = chain.request()

        val authRequest = originalRequest.newBuilder().newAuthBuilder().build()

        val response = chain.proceed(authRequest)

        return response
    }

    private fun Request.Builder.newAuthBuilder() =
        this.addHeader(AUTHORIZATION, "$BEARER ${tokenRepository.getCachedAccessToken()}")

    companion object {
        private const val AUTHORIZATION = "Authorization"
        private const val BEARER = "Bearer"
    }
}
