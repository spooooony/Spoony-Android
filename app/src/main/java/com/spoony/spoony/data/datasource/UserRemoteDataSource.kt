package com.spoony.spoony.data.datasource

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.dto.response.BasicUserInfoResponseDto

interface UserRemoteDataSource {
    suspend fun getMyInfo(): BaseResponse<BasicUserInfoResponseDto>

    suspend fun getUserInfoById(userId: Int): BaseResponse<BasicUserInfoResponseDto>
    suspend fun followUser(userId: Int): BaseResponse<Unit>
    suspend fun unfollowUser(userId: Int): BaseResponse<Unit>
}
