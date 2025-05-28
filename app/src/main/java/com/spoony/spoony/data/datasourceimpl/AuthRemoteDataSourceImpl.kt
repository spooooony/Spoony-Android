package com.spoony.spoony.data.datasourceimpl

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.datasource.AuthRemoteDataSource
import com.spoony.spoony.data.dto.request.SignInRequestDto
import com.spoony.spoony.data.dto.request.SignUpRequestDto
import com.spoony.spoony.data.dto.response.SignInResponseDto
import com.spoony.spoony.data.dto.response.SignUpResponseDto
import com.spoony.spoony.data.dto.response.TokenResponseDto
import com.spoony.spoony.data.service.AuthService
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authService: AuthService
) : AuthRemoteDataSource {
    override suspend fun signIn(
        token: String,
        platform: String
    ): BaseResponse<SignInResponseDto> =
        authService.signIn(
            authorization = "$BEARER $token",
            signInRequestDto = SignInRequestDto(platform)
        )

    override suspend fun signUp(
        token: String,
        platform: String,
        userName: String,
        birth: String?,
        regionId: Int?,
        introduction: String?
    ): BaseResponse<SignUpResponseDto> =
        authService.signUp(
            authorization = "$BEARER $token",
            signUpRequestDto = SignUpRequestDto(
                platform = platform,
                userName = userName,
                birth = birth,
                regionId = regionId,
                introduction = introduction
            )
        )

    override suspend fun withDraw(token: String): BaseResponse<Unit> = authService.withDraw("$BEARER $token")

    override suspend fun refreshToken(token: String): BaseResponse<TokenResponseDto> =
        authService.refreshToken(token)

    companion object {
        private const val BEARER = "Bearer"
    }
}
