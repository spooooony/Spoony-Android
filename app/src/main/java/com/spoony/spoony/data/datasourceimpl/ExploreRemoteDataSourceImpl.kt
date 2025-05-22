package com.spoony.spoony.data.datasourceimpl

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.datasource.ExploreRemoteDataSource
import com.spoony.spoony.data.dto.response.FeedsResponseDto
import com.spoony.spoony.data.dto.response.PlaceReviewListResponseDto
import com.spoony.spoony.data.dto.response.UserListSearchResponseDto
import com.spoony.spoony.data.service.ExploreService
import javax.inject.Inject

class ExploreRemoteDataSourceImpl @Inject constructor(
    private val exploreService: ExploreService
) : ExploreRemoteDataSource {
    override suspend fun getFeedList(
        categoryId: Int,
        query: String,
        sortBy: String
    ): BaseResponse<FeedsResponseDto> = exploreService.getFeedsList(
        categoryId = categoryId,
        query = query,
        sortBy = sortBy
    )

    override suspend fun getPlaceReviewByKeyword(query: String): BaseResponse<PlaceReviewListResponseDto> = exploreService.getPlaceReviewByKeyword(
        query = query
    )

    override suspend fun getUserListByKeyword(query: String): BaseResponse<UserListSearchResponseDto> = exploreService.getUserListByKeyword(
        query = query
    )
}
