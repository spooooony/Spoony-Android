package com.spoony.spoony.data.datasource

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.dto.response.ExplorePlaceReviewListFollowingResponseDto
import com.spoony.spoony.data.dto.response.FeedsResponseDto
import com.spoony.spoony.data.dto.response.PlaceReviewListResponseDto
import com.spoony.spoony.data.dto.response.UserListSearchResponseDto

interface ExploreRemoteDataSource {
    suspend fun getFeedList(
        categoryId: Int,
        query: String,
        sortBy: String
    ): BaseResponse<FeedsResponseDto>

    suspend fun getPlaceReviewListFollowing(): BaseResponse<ExplorePlaceReviewListFollowingResponseDto>

    suspend fun getPlaceReviewByKeyword(
        query: String
    ): BaseResponse<PlaceReviewListResponseDto>

    suspend fun getUserListByKeyword(
        query: String
    ): BaseResponse<UserListSearchResponseDto>
}
