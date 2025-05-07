package com.spoony.spoony.domain.repository

import com.spoony.spoony.domain.entity.FeedEntity

interface ExploreRepository {
    suspend fun getFeedList(
        categoryId: Int,
        locationQuery: String,
        sortBy: String
    ): Result<List<FeedEntity>>
}
