package com.spoony.spoony.data.datasource.local

import kotlinx.coroutines.flow.Flow

interface RecentSearchDataSource {
    fun getRecentReviewQueries(): Flow<List<String>>
    fun getRecentUserQueries(): Flow<List<String>>

    suspend fun setReviewQueries(list: List<String>)
    suspend fun setUserQueries(list: List<String>)

    suspend fun clearReviewQueries()
    suspend fun clearUserQueries()
}
