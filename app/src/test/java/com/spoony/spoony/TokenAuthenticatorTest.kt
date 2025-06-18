package com.spoony.spoony

import com.spoony.spoony.core.network.TokenAuthenticator
import com.spoony.spoony.domain.entity.TokenEntity
import com.spoony.spoony.domain.repository.AuthRepository
import com.spoony.spoony.domain.repository.TokenRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TokenAuthenticatorTest {
    private lateinit var tokenRepository: TokenRepository
    private lateinit var authRepository: AuthRepository
    private lateinit var authenticator: TokenAuthenticator

    @Before
    fun setUp() {
        tokenRepository = mockk()
        authRepository = mockk()
        authenticator = TokenAuthenticator(tokenRepository, authRepository)
    }

    private fun createResponseWithToken(token: String?): Response {
        val requestBuilder = Request.Builder().url("https://www.test.test")
        if (token != null) requestBuilder.header("Authorization", "Bearer $token")
        val request = requestBuilder.build()

        return Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(401)
            .message("Unauthorized")
            .build()
    }

    @Test
    fun 토큰_재발급_성공시_토큰_저장_후_재요청() {
        val newTokenEntity = TokenEntity(accessToken = "newAccessToken", refreshToken = "newRefreshToken")

        coEvery { tokenRepository.getAccessToken() } returns flowOf("oldAccessToken")
        coEvery { tokenRepository.getRefreshToken() } returns flowOf("oldRefreshToken")
        coEvery { authRepository.refreshToken("oldRefreshToken") } returns Result.success(newTokenEntity)
        coEvery { tokenRepository.updateTokens(newTokenEntity) } just Runs

        val response = createResponseWithToken("oldAccessToken")

        val result = authenticator.authenticate(null, response)

        assertNotNull(result)
        assertEquals("newAccessToken", result?.header("Authorization")?.removePrefix("Bearer")?.trim())
        coVerify { authRepository.refreshToken("oldRefreshToken") }
        coVerify { tokenRepository.updateTokens(newTokenEntity) }
    }

    @Test
    fun 이미_토큰이_재발급된_경우_재요청() {
        coEvery { tokenRepository.getAccessToken() } returns flowOf("newAccessToken")

        val response = createResponseWithToken("oldAccessToken")

        val result = authenticator.authenticate(null, response)

        assertNotNull(result)
        assertEquals("newAccessToken", result?.header("Authorization")?.removePrefix("Bearer")?.trim())
        coVerify(exactly = 0) { authRepository.refreshToken(any()) }
    }

    @Test
    fun 재발급_실패시_토큰_초기화_후_null_반환() {
        coEvery { tokenRepository.getAccessToken() } returns flowOf("oldAccessToken")
        coEvery { tokenRepository.getRefreshToken() } returns flowOf("oldRefreshToken")
        coEvery { authRepository.refreshToken("oldRefreshToken") } returns Result.failure(Exception("fail"))
        coEvery { tokenRepository.clearTokens() } just Runs

        val response = createResponseWithToken("oldAccessToken")

        val result = authenticator.authenticate(null, response)

        assertNull(result)
        coVerify { tokenRepository.clearTokens() }
    }

    @Test
    fun 이미_토큰_재발급에_실패한_경우_null_반환() {
        coEvery { tokenRepository.getAccessToken() } returns flowOf("")
        coEvery { tokenRepository.getRefreshToken() } returns flowOf("")

        val response = createResponseWithToken("oldAccessToken")

        val result = authenticator.authenticate(null, response)

        assertNull(result)
        coVerify(exactly = 0) { authRepository.refreshToken(any()) }
    }

    @Test
    fun 토큰이_이미_없을_때() {
        coEvery { tokenRepository.getAccessToken() } returns flowOf("")
        coEvery { tokenRepository.getRefreshToken() } returns flowOf("")

        val nullResponse = createResponseWithToken(null)
        val nullResult = authenticator.authenticate(null, nullResponse)

        val blankResponse = createResponseWithToken("")
        val blankResult = authenticator.authenticate(null, blankResponse)

        assertNull(nullResult)
        assertNull(blankResult)
        coVerify(exactly = 0) { authRepository.refreshToken(any()) }
    }
}
