package com.spoony.spoony.data.service

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.dto.request.FollowRequestDto
import com.spoony.spoony.data.dto.response.UserInfoResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {
    @GET("/api/v1/user/{targetUserId}")
    suspend fun getUserInfoById(
        @Path("targetUserId") targetUserId: Int
    ): BaseResponse<UserInfoResponseDto>

    @POST("/api/v1/user/follow")
    suspend fun followUser(
        @Body followRequestDto: FollowRequestDto
    ): BaseResponse<Unit>

    @DELETE("/api/v1/user/follow")
    suspend fun unfollowUser(
        @Body followRequestDto: FollowRequestDto
    ): BaseResponse<Unit>
}
