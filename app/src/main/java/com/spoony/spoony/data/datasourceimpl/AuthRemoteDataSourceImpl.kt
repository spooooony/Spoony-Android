package com.spoony.spoony.data.datasourceimpl

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.datasource.AuthRemoteDataSource
import com.spoony.spoony.data.dto.request.SignInRequestDto
import com.spoony.spoony.data.dto.response.SignInResponseDto
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
            authorization = "Bearer $token",
            signInRequestDto = SignInRequestDto(platform)
        )
}
