package com.spoony.spoony.data.service

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.dto.response.UserPageReviewResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReviewService {
    @GET("/api/v1/user/reviews")
    suspend fun getMyReview(): BaseResponse<UserPageReviewResponseDto>

    @GET("/api/v1/user/reviews/{targetUserId}")
    suspend fun getOtherReview(
        @Path("targetUserId") targetUserId: Int,
        @Query("isLocalReview") isLocalReview: Boolean
    ): BaseResponse<UserPageReviewResponseDto>
}
