package com.spoony.spoony.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.spoony.spoony.core.database.entity.ExploreRecentSearchEntity
import com.spoony.spoony.core.database.entity.ExploreRecentSearchType
import kotlinx.coroutines.flow.Flow

@Dao
interface ExploreRecentSearchDao {

    @Query("SELECT * FROM explore_recent_search WHERE type = :type ORDER BY timestamp DESC")
    fun getQueriesByType(type: ExploreRecentSearchType): Flow<List<ExploreRecentSearchEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exploreRecentEntity: ExploreRecentSearchEntity)

    @Query("DELETE FROM explore_recent_search WHERE type = :type AND keyword = :keyword")
    suspend fun deleteByKeyword(type: ExploreRecentSearchType, keyword: String)

    @Query("DELETE FROM explore_recent_search WHERE type = :type")
    suspend fun clearByType(type: ExploreRecentSearchType)

    @Query("SELECT COUNT(*) FROM explore_recent_search WHERE type = :type")
    suspend fun getSearchCountByType(type: ExploreRecentSearchType): Int

    @Query("""
        DELETE FROM explore_recent_search
        WHERE keyword IN (
            SELECT keyword FROM explore_recent_search
            WHERE type = :type
            ORDER BY timestamp ASC
            LIMIT 1
        ) AND type = :type
    """)
    suspend fun deleteOldestSearchByType(type: ExploreRecentSearchType)

    @Transaction
    suspend fun addKeyword(type: ExploreRecentSearchType, keyword: String) {
        val trimmed = keyword.trim()
        if (trimmed.isBlank()) return

        insert(ExploreRecentSearchEntity(keyword = trimmed, type = type))

        if (getSearchCountByType(type) > MAX_RECENT_SEARCHES) {
            deleteOldestSearchByType(type)
        }
    }
    companion object {
        private const val MAX_RECENT_SEARCHES = 6
    }
}