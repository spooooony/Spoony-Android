package com.spoony.spoony.data.datasource

import com.spoony.spoony.data.dto.base.BaseResponse
import com.spoony.spoony.data.dto.response.UserInfoResponseDto

interface AuthRemoteDataSource {
    suspend fun getUserInfo(userId: Int): BaseResponse<UserInfoResponseDto>
}
