package com.spoony.spoony.data.service

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.dto.response.UserInfoResponseDto
import com.spoony.spoony.data.dto.response.UserSpoonCountResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface AuthService {
    @GET("/api/v1/user")
    suspend fun getUserInfo(): BaseResponse<UserInfoResponseDto>

    @GET("/api/v1/user/{userId}")
    suspend fun getUserInfoById(
        @Path("userId") userId: Int
    ): BaseResponse<UserInfoResponseDto>

    @GET("/api/v1/spoon")
    suspend fun getSpoonCount(): BaseResponse<UserSpoonCountResponseDto>
}
