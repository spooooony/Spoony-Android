package com.spoony.spoony.data.service

import com.spoony.spoony.data.dto.base.BaseResponseDTO
import com.spoony.spoony.data.dto.response.GetPostResponseDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface PostService {
    companion object {
        const val API = "api"
        const val V1 = "v1"
        const val POST = "post"
    }

    @GET("/$API/$V1/$POST/{userId}/{postId}")
    suspend fun getPost(
        @Path("userId") userId: Int,
        @Path("postId") postId: Int
    ): BaseResponseDTO<GetPostResponseDTO>
}
