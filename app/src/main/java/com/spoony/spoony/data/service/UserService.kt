package com.spoony.spoony.data.service

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.dto.response.BasicUserInfoResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("/api/v1/user")
    suspend fun getMyInfo(): BaseResponse<BasicUserInfoResponseDto>

    @GET("/api/v1/user/{userId}")
    suspend fun getUserInfoById(
        @Path("userId") userId: Int
    ): BaseResponse<BasicUserInfoResponseDto>
}
