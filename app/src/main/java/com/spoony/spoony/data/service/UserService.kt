package com.spoony.spoony.data.service

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.dto.response.BasicUserInfoResponseDto
import com.spoony.spoony.data.dto.response.GetRegionListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {
    @GET("/api/v1/user")
    suspend fun getMyInfo(): BaseResponse<BasicUserInfoResponseDto>

    @GET("/api/v1/user/{userId}")
    suspend fun getUserInfoById(
        @Path("userId") userId: Int
    ): BaseResponse<BasicUserInfoResponseDto>

    @GET("/api/v1/user/region")
    suspend fun getRegionList(): BaseResponse<GetRegionListDto>

    @GET("/api/v1/user/exists")
    suspend fun checkUserNameExist(
        @Query("userName") userName: String
    ): BaseResponse<Boolean>
}
