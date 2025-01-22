package com.spoony.spoony.data.service

import com.spoony.spoony.data.dto.base.BaseResponse
import com.spoony.spoony.data.dto.request.PostScoopRequestDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface PostService {
    companion object {
        const val API = "api"
        const val V1 = "v1"
        const val POST = "post"
    }

    @POST("/$API/$V1/$POST/scoop")
    suspend fun postScoopPost(
        @Body postScoopRequestDTO: PostScoopRequestDTO
    ): BaseResponse<Boolean>
}
