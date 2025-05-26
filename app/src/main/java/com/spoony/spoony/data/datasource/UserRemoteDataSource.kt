package com.spoony.spoony.data.datasource

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.dto.response.BasicUserInfoResponseDto
import com.spoony.spoony.data.dto.response.FollowListResponseDto

interface UserRemoteDataSource {
    suspend fun getMyInfo(): BaseResponse<BasicUserInfoResponseDto>

    suspend fun getUserInfoById(userId: Int): BaseResponse<BasicUserInfoResponseDto>

    suspend fun followUser(userId: Int): BaseResponse<Unit>

    suspend fun unfollowUser(userId: Int): BaseResponse<Unit>

    suspend fun getMyFollowings(): BaseResponse<FollowListResponseDto>

    suspend fun getMyFollowers(): BaseResponse<FollowListResponseDto>

    suspend fun getOtherFollowings(targetUserId: Int): BaseResponse<FollowListResponseDto>

    suspend fun getOtherFollowers(targetUserId: Int): BaseResponse<FollowListResponseDto>
}
