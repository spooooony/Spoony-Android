package com.spoony.spoony.data.service

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.dto.response.TokenResponseDto
import retrofit2.http.Header
import retrofit2.http.POST

interface TokenRefreshService {
    @POST("api/v1/auth/refresh")
    suspend fun refreshToken(
        @Header("Authorization") authorization: String
    ): BaseResponse<TokenResponseDto>
}
