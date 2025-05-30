package com.spoony.spoony.data.datasourceimpl

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.datasource.ExploreRemoteDataSource
import com.spoony.spoony.data.dto.response.ExplorePlaceReviewFilteredResponseDto
import com.spoony.spoony.data.dto.response.ExplorePlaceReviewFollowingResponseDto
import com.spoony.spoony.data.dto.response.PlaceReviewListResponseDto
import com.spoony.spoony.data.dto.response.UserListSearchResponseDto
import com.spoony.spoony.data.service.ExploreService
import javax.inject.Inject

class ExploreRemoteDataSourceImpl @Inject constructor(
    private val exploreService: ExploreService
) : ExploreRemoteDataSource {
    override suspend fun getPlaceReviewListFollowing(): BaseResponse<ExplorePlaceReviewFollowingResponseDto> =
        exploreService.getPlaceReviewListFollowing()

    override suspend fun getPlaceReviewByKeyword(query: String): BaseResponse<PlaceReviewListResponseDto> = exploreService.getPlaceReviewByKeyword(
        query = query
    )

    override suspend fun getUserListByKeyword(query: String): BaseResponse<UserListSearchResponseDto> = exploreService.getUserListByKeyword(
        query = query
    )

    override suspend fun getPlaceReviewListFiltered(categoryIds: List<Int>?, regionIds: List<Int>?, ageGroups: List<String>?, sortBy: String?, cursor: Int?, size: Int?): BaseResponse<ExplorePlaceReviewFilteredResponseDto> =
        exploreService.getPlaceReviewListFiltered(
            categoryIds = categoryIds,
            regionIds = regionIds,
            ageGroups = ageGroups,
            sortBy = sortBy,
            cursor = cursor,
            size = size
        )
}
