package com.spoony.spoony.data.datasource

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.dto.response.UserInfoResponseDto

interface AuthRemoteDataSource {
    suspend fun getUserInfo(): BaseResponse<UserInfoResponseDto>
}
