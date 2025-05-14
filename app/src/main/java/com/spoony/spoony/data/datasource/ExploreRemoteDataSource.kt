package com.spoony.spoony.data.datasource

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.dto.response.FeedsResponseDto
import com.spoony.spoony.data.dto.response.PlaceReviewListResponseDto
import com.spoony.spoony.data.dto.response.UserListSearchResponseDto

interface ExploreRemoteDataSource {
    suspend fun getFeedList(
        categoryId: Int,
        query: String,
        sortBy: String
    ): BaseResponse<FeedsResponseDto>

    suspend fun getPlaceReviewSearchByKeyword(
        query: String
    ): BaseResponse<PlaceReviewListResponseDto>

    suspend fun getUserListSearchByKeyword(
        query: String
    ): BaseResponse<UserListSearchResponseDto>
}
