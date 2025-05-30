package com.spoony.spoony.domain.repository

import com.spoony.spoony.core.database.entity.ExploreRecentSearchType
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

    suspend fun getPlaceReviewByKeyword(
        query: String
    ): Result<List<PlaceReviewEntity>>

    suspend fun getUserListByKeyword(
        query: String
    ): Result<List<UserEntity>>

    suspend fun getExploreRecentSearches(
        type: ExploreRecentSearchType
    ): Result<List<String>>

    suspend fun deleteExploreRecentSearch(
        type: ExploreRecentSearchType,
        searchText: String
    ): Result<Unit>

    suspend fun clearExploreRecentSearch(
        type: ExploreRecentSearchType
    ): Result<Unit>

    suspend fun insertExploreRecentSearch(
        type: ExploreRecentSearchType,
        searchText: String
    ): Result<Unit>
}
