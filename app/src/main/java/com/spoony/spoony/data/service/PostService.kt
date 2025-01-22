package com.spoony.spoony.data.service

import com.spoony.spoony.data.dto.base.BaseResponse
import com.spoony.spoony.data.dto.request.PostScoopRequestDto
import com.spoony.spoony.data.dto.request.RegisterPostRequestDto
import com.spoony.spoony.data.dto.response.GetPostResponseDto
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface PostService {
    @GET("/api/v1/post/{userId}/{postId}")
    suspend fun getPost(
        @Path("userId") userId: Int,
        @Path("postId") postId: Int
    ): BaseResponse<GetPostResponseDto>

    @POST("/api/v1/post/scoop")
    suspend fun postScoopPost(
        @Body postScoopRequestDto: PostScoopRequestDto
    ): BaseResponse<Boolean>

    @Multipart
    @POST("/api/v1/post")
    suspend fun registerPost(
        @Part("request") request: RegisterPostRequestDto,
        @Part images: List<MultipartBody.Part>
    ): BaseResponse<Unit>
}
