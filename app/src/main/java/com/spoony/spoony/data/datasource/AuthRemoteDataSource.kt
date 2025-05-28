package com.spoony.spoony.data.datasource

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.dto.response.SignInResponseDto
import com.spoony.spoony.data.dto.response.SignUpResponseDto
import com.spoony.spoony.data.dto.response.TokenResponseDto

interface AuthRemoteDataSource {
    suspend fun signIn(
        token: String,
        platform: String
    ): BaseResponse<SignInResponseDto>

    suspend fun signUp(
        token: String,
        platform: String,
        userName: String,
        birth: String?,
        regionId: Int?,
        introduction: String?
    ): BaseResponse<SignUpResponseDto>

    suspend fun signOut(token: String): BaseResponse<Unit>

    suspend fun withDraw(token: String): BaseResponse<Unit>

    suspend fun refreshToken(token: String): BaseResponse<TokenResponseDto>
}
