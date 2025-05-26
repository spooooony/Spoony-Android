package com.spoony.spoony.data.service

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.dto.response.BasicUserInfoResponseDto
import com.spoony.spoony.data.dto.request.FollowRequestDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {
    @GET("/api/v1/user")
    suspend fun getMyInfo(): BaseResponse<BasicUserInfoResponseDto>

    @GET("/api/v1/user/{targetUserId}")
    suspend fun getUserInfoById(
        @Path("targetUserId") targetUserId: Int
    ): BaseResponse<BasicUserInfoResponseDto>

    @POST("/api/v1/user/follow")
    suspend fun followUser(
        @Body followRequestDto: FollowRequestDto
    ): BaseResponse<Unit>

    @HTTP(method = "DELETE", path = "/api/v1/user/follow", hasBody = true)
    suspend fun unfollowUser(
        @Body followRequestDto: FollowRequestDto
    ): BaseResponse<Unit>
}
