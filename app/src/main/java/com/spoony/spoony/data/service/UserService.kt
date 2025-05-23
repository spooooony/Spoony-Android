package com.spoony.spoony.data.service

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.dto.response.GetRegionListDto
import com.spoony.spoony.data.dto.response.UserInfoResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("/api/v1/user/{userId}")
    suspend fun getUserInfoById(
        @Path("userId") userId: Int
    ): BaseResponse<UserInfoResponseDto>

    @GET("/api/v1/user/region")
    suspend fun getRegionList(): BaseResponse<GetRegionListDto>
}
