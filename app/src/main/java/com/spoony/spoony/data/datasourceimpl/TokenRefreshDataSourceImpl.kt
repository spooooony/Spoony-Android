package com.spoony.spoony.data.datasourceimpl

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.datasource.TokenRefreshDataSource
import com.spoony.spoony.data.dto.response.TokenResponseDto
import com.spoony.spoony.data.service.TokenRefreshService
import javax.inject.Inject

class TokenRefreshDataSourceImpl @Inject constructor(
    private val tokenRefreshService: TokenRefreshService
) : TokenRefreshDataSource {
    override suspend fun refreshToken(token: String): BaseResponse<TokenResponseDto> =
        tokenRefreshService.refreshToken(token)
}
