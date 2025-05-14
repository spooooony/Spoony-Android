package com.spoony.spoony.domain.repository

import com.spoony.spoony.domain.entity.FeedEntity
import com.spoony.spoony.domain.entity.PlaceReviewEntity
import com.spoony.spoony.domain.entity.UserEntity

interface ExploreRepository {
    suspend fun getFeedList(
        categoryId: Int,
        locationQuery: String,
        sortBy: String
    ): Result<List<FeedEntity>>

    suspend fun getAllFeedList(): Result<List<PlaceReviewEntity>>

    suspend fun getPlaceReviewSearchByKeyword(
        query: String
    ): Result<List<PlaceReviewEntity>>

    suspend fun getUserListSearchByKeyword(
        query: String
    ): Result<List<UserEntity>>
}
