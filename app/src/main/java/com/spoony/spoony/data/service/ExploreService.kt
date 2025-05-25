package com.spoony.spoony.data.service

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.dto.response.ExplorePlaceReviewListFollowingResponseDto
import com.spoony.spoony.data.dto.response.FeedsResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ExploreService {
    @GET("/api/v1/feed/{categoryId}")
    suspend fun getFeedsList(
        @Path("categoryId") categoryId: Int,
        @Query("query") query: String,
        @Query("sortBy") sortBy: String
    ): BaseResponse<FeedsResponseDto>

    @GET("/api/v1/feed/following")
    suspend fun getPlaceReviewListFollowing(): BaseResponse<ExplorePlaceReviewListFollowingResponseDto>
}
