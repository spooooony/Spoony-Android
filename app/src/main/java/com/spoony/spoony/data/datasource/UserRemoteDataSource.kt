package com.spoony.spoony.data.datasource

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.dto.response.UserInfoResponseDto

interface UserRemoteDataSource {
    suspend fun getUserInfoById(userId: Int): BaseResponse<UserInfoResponseDto>
    suspend fun followUser(userId: Int): BaseResponse<Unit>
    suspend fun unfollowUser(userId: Int): BaseResponse<Unit>
}
