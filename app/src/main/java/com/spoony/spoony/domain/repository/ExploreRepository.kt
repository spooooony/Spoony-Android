package com.spoony.spoony.domain.repository

import com.spoony.spoony.domain.entity.FeedEntity
import com.spoony.spoony.domain.entity.PlaceReviewEntity

interface ExploreRepository {
    suspend fun getFeedList(
        categoryId: Int,
        locationQuery: String,
        sortBy: String
    ): Result<List<FeedEntity>>

    suspend fun getAllFeedList(): Result<List<PlaceReviewEntity>>

    suspend fun getPlaceReviewListFollowing(): Result<List<PlaceReviewEntity>>
}
