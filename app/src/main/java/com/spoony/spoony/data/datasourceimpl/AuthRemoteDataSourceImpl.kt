package com.spoony.spoony.data.datasourceimpl

import com.spoony.spoony.data.datasource.AuthRemoteDataSource
import com.spoony.spoony.data.dto.base.BaseResponse
import com.spoony.spoony.data.dto.response.UserInfoResponseDto
import com.spoony.spoony.data.dto.response.UserSpoonCountResponseDto
import com.spoony.spoony.data.service.AuthService
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authService: AuthService
) : AuthRemoteDataSource {
    override suspend fun getUserInfo(): BaseResponse<UserInfoResponseDto> =
        authService.getUserInfo()
    override suspend fun getSpoonCount(): BaseResponse<UserSpoonCountResponseDto> =
        authService.getSpoonCount()
}
