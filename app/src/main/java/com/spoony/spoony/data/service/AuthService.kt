package com.spoony.spoony.data.service

import com.spoony.spoony.data.dto.base.BaseResponse
import com.spoony.spoony.data.dto.response.UserInfoResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface AuthService {
    @GET("/api/v1/user/{userId}")
    suspend fun getUserInfo(
        @Path("userId") userId: Int
    ): BaseResponse<UserInfoResponseDto>
}
