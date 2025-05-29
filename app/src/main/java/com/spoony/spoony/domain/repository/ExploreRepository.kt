package com.spoony.spoony.domain.repository

import com.spoony.spoony.domain.entity.ExplorePlaceReviewResultEntity
import com.spoony.spoony.domain.entity.PlaceReviewEntity
import com.spoony.spoony.domain.entity.UserEntity

interface ExploreRepository {
    suspend fun getPlaceReviewListFollowing(): Result<List<PlaceReviewEntity>>

    suspend fun getPlaceReviewByKeyword(
        query: String
    ): Result<List<PlaceReviewEntity>>

    suspend fun getUserListByKeyword(
        query: String
    ): Result<List<UserEntity>>

    suspend fun getPlaceReviewListFiltered(
        categoryIds: List<Int>?,
        regionIds: List<Int>?,
        ageGroups: List<String>?,
        sortBy: String?,
        cursor: Int?,
        size: Int?
    ): Result<ExplorePlaceReviewResultEntity>
}
