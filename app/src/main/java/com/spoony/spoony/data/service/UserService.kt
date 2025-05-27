package com.spoony.spoony.data.service

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.dto.request.FollowRequestDto
import com.spoony.spoony.data.dto.request.ProfileUpdateRequestDto
import com.spoony.spoony.data.dto.response.BasicUserInfoResponseDto
import com.spoony.spoony.data.dto.response.FollowListResponseDto
import com.spoony.spoony.data.dto.response.GetRegionListDto
import com.spoony.spoony.data.dto.response.ProfileImageResponseDto
import com.spoony.spoony.data.dto.response.ProfileInfoResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {
    @GET("/api/v1/user")
    suspend fun getMyInfo(): BaseResponse<BasicUserInfoResponseDto>

    @GET("/api/v1/user/{targetUserId}")
    suspend fun getUserInfoById(
        @Path("targetUserId") targetUserId: Int
    ): BaseResponse<BasicUserInfoResponseDto>

    @GET("/api/v1/user/region")
    suspend fun getRegionList(): BaseResponse<GetRegionListDto>

    @GET("/api/v1/user/exists")
    suspend fun checkUserNameExist(
        @Query("userName") userName: String
    ): BaseResponse<Boolean>

    @POST("/api/v1/user/follow")
    suspend fun followUser(
        @Body followRequestDto: FollowRequestDto
    ): BaseResponse<Unit>

    @HTTP(method = "DELETE", path = "/api/v1/user/follow", hasBody = true)
    suspend fun unfollowUser(
        @Body followRequestDto: FollowRequestDto
    ): BaseResponse<Unit>

    @GET("/api/v1/user/followings")
    suspend fun getMyFollowings(): BaseResponse<FollowListResponseDto>

    @GET("/api/v1/user/followers")
    suspend fun getMyFollowers(): BaseResponse<FollowListResponseDto>

    @GET("/api/v1/user/followings/{targetUserId}")
    suspend fun getOtherFollowings(
        @Path("targetUserId") targetUserId: Int
    ): BaseResponse<FollowListResponseDto>

    @GET("/api/v1/user/followers/{targetUserId}")
    suspend fun getOtherFollowers(
        @Path("targetUserId") targetUserId: Int
    ): BaseResponse<FollowListResponseDto>

    @GET("/api/v1/user/profile")
    suspend fun getMyProfileInfo(): BaseResponse<ProfileInfoResponseDto>

    @GET("/api/v1/user/profile/images")
    suspend fun getMyProfileImages(): BaseResponse<ProfileImageResponseDto>

    @PATCH("/api/v1/user/profile")
    suspend fun updateMyProfileInfo(
        @Body profileUpdateRequestDto: ProfileUpdateRequestDto
    ): BaseResponse<Unit>
}
