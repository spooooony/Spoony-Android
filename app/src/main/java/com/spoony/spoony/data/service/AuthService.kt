package com.spoony.spoony.data.service

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.dto.response.UserInfoResponseDto
import retrofit2.http.GET

interface AuthService {
    @GET("/api/v1/user")
    suspend fun getUserInfo(): BaseResponse<UserInfoResponseDto>
}
