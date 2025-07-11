package com.spoony.spoony.data.datasource

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.dto.response.ExplorePlaceReviewFilteredResponseDto
import com.spoony.spoony.data.dto.response.ExplorePlaceReviewFollowingResponseDto
import com.spoony.spoony.data.dto.response.PlaceReviewListResponseDto
import com.spoony.spoony.data.dto.response.UserListSearchResponseDto

interface ExploreRemoteDataSource {
    suspend fun getPlaceReviewListFollowing(): BaseResponse<ExplorePlaceReviewFollowingResponseDto>

    suspend fun getPlaceReviewByKeyword(
        query: String
    ): BaseResponse<PlaceReviewListResponseDto>

    suspend fun getUserListByKeyword(
        query: String
    ): BaseResponse<UserListSearchResponseDto>

    suspend fun getPlaceReviewListFiltered(
        categoryIds: List<Int>?,
        regionIds: List<Int>?,
        ageGroups: List<String>?,
        sortBy: String?,
        cursor: String?,
        size: Int?
    ): BaseResponse<ExplorePlaceReviewFilteredResponseDto>
}
