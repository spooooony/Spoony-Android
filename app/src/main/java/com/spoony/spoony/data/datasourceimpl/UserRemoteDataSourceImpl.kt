package com.spoony.spoony.data.datasourceimpl

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.datasource.UserRemoteDataSource
import com.spoony.spoony.data.dto.request.FollowRequestDto
import com.spoony.spoony.data.dto.response.UserInfoResponseDto
import com.spoony.spoony.data.service.UserService
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val userService: UserService
) : UserRemoteDataSource {
    override suspend fun getUserInfoById(userId: Int): BaseResponse<UserInfoResponseDto> =
        userService.getUserInfoById(userId)
    override suspend fun followUser(userId: Int): BaseResponse<Unit> =
        userService.followUser(
            FollowRequestDto(targetUserId = userId)
        )
    override suspend fun unfollowUser(userId: Int): BaseResponse<Unit> =
        userService.unfollowUser(
            FollowRequestDto(targetUserId = userId)
        )
}
