package com.spoony.spoony.data.service

import com.spoony.spoony.data.dto.base.BaseResponse
import com.spoony.spoony.data.dto.request.AddMapRequestDto
import com.spoony.spoony.data.dto.request.PostScoopRequestDto
import com.spoony.spoony.data.dto.response.GetPostResponseDto
import com.spoony.spoony.data.dto.response.ZzimLocationResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PostService {
    @GET("/api/v1/post/{userId}/{postId}")
    suspend fun getPost(
        @Path("userId") userId: Int,
        @Path("postId") postId: Int
    ): BaseResponse<GetPostResponseDto>

    @GET("/api/v1/post/zzim/location/{userId}/{locationId}")
    suspend fun getZzimByLocation(
        @Path("userId") userId: Int,
        @Path("locationId") locationId: Int
    ): BaseResponse<ZzimLocationResponseDto>

    @POST("/api/v1/post/zzim")
    suspend fun postAddMapPost(
        @Body addMapRequestDto: AddMapRequestDto
    ): BaseResponse<Boolean>

    @POST("/api/v1/post/scoop")
    suspend fun postScoopPost(
        @Body postScoopRequestDto: PostScoopRequestDto
    ): BaseResponse<Boolean>

    @DELETE("/api/v1/post/zzim/{userId}/{postId}")
    suspend fun deletePinMap(
        @Path("userId") userId: Int,
        @Path("postId") postId: Int
    ): BaseResponse<Boolean>
}
