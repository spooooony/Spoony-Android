package com.spoony.spoony.data.service

import com.spoony.spoony.data.dto.base.BaseResponse
import com.spoony.spoony.data.dto.request.PostScoopRequestDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface PostService {
    @POST("/api/v1/post/scoop")
    suspend fun postScoopPost(
        @Body postScoopRequestDTO: PostScoopRequestDTO
    ): BaseResponse<Boolean>
}
