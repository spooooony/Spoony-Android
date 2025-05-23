package com.spoony.spoony.data.datasource

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.dto.response.SignInResponseDto

interface AuthRemoteDataSource {
    suspend fun signIn(
        token: String,
        platform: String
    ): BaseResponse<SignInResponseDto>
}
