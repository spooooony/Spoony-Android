package com.spoony.spoony.data.datasource

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.dto.response.UserInfoResponseDto
import com.spoony.spoony.data.dto.response.UserSpoonCountResponseDto

interface AuthRemoteDataSource {
    suspend fun getUserInfo(): BaseResponse<UserInfoResponseDto>
    suspend fun getSpoonCount(): BaseResponse<UserSpoonCountResponseDto>
}
