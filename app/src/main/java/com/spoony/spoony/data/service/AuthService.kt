package com.spoony.spoony.data.service

import com.spoony.spoony.data.dto.base.BaseResponse
import com.spoony.spoony.data.dto.response.UserInfoResponseDto
import com.spoony.spoony.data.dto.response.UserSpoonCountResponseDto
import retrofit2.http.GET

interface AuthService {
    @GET("/api/v1/user")
    suspend fun getUserInfo(): BaseResponse<UserInfoResponseDto>

    @GET("/api/v1/spoon")
    suspend fun getSpoonCount(): BaseResponse<UserSpoonCountResponseDto>
}
