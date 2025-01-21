package com.spoony.spoony.domain.repository

import com.spoony.spoony.domain.entity.CategoryEntity
import com.spoony.spoony.domain.entity.FeedEntity

interface ExploreRepository {
    suspend fun getCategoryList(): Result<List<CategoryEntity>>

    suspend fun getFeedList(
        userId: Int,
        categoryId: Int,
        locationQuery: String,
        sortBy: String
    ): Result<List<FeedEntity>>
}
