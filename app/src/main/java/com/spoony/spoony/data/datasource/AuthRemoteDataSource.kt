package com.spoony.spoony.data.datasource

import com.spoony.spoony.data.dto.base.BaseResponse
import com.spoony.spoony.data.dto.response.UserInfoResponseDto
import com.spoony.spoony.data.dto.response.UserSpoonCountResponseDto

interface AuthRemoteDataSource {
    suspend fun getUserInfo(): BaseResponse<UserInfoResponseDto>
    suspend fun getSpoonCount(): BaseResponse<UserSpoonCountResponseDto>
}
