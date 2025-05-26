package com.spoony.spoony.data.datasource

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.dto.response.TokenResponseDto

interface TokenRefreshDataSource {
    suspend fun refreshToken(token: String): BaseResponse<TokenResponseDto>
}
