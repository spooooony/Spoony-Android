package com.spoony.spoony.data.service

import com.spoony.spoony.data.dto.base.BaseResponse
import com.spoony.spoony.data.dto.response.GetPostResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface PostService {
    @GET("/api/v1/post/{userId}/{postId}")
    suspend fun getPost(
        @Path("userId") userId: Int,
        @Path("postId") postId: Int
    ): BaseResponse<GetPostResponseDto>
}
