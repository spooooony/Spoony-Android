package com.spoony.spoony.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.spoony.spoony.core.database.entity.ExploreRecentSearchEntity
import com.spoony.spoony.core.database.entity.ExploreRecentSearchType

@Dao
interface ExploreRecentSearchDao {

    @Query("SELECT * FROM explore_recent_search WHERE type = :type ORDER BY timestamp DESC")
    suspend fun getQueriesExploreRecentSearch(type: ExploreRecentSearchType): List<ExploreRecentSearchEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExploreRecentSearch(exploreRecentEntity: ExploreRecentSearchEntity)

    @Query("DELETE FROM explore_recent_search WHERE type = :type AND keyword = :keyword")
    suspend fun deleteExploreRecentSearch(type: ExploreRecentSearchType, keyword: String)

    @Query("DELETE FROM explore_recent_search WHERE type = :type")
    suspend fun clearExploreRecentSearch(type: ExploreRecentSearchType)

    @Query("SELECT COUNT(*) FROM explore_recent_search WHERE type = :type")
    suspend fun getCountExploreRecentSearch(type: ExploreRecentSearchType): Int

    @Query(
        """
        DELETE FROM explore_recent_search
        WHERE keyword IN (
            SELECT keyword FROM explore_recent_search
            WHERE type = :type
            ORDER BY timestamp ASC
            LIMIT 1
        ) AND type = :type
    """
    )
    suspend fun deleteOldestSearchByType(type: ExploreRecentSearchType)

    @Transaction
    suspend fun insertKeywordWithLimit(type: ExploreRecentSearchType, keyword: String) {
        insertExploreRecentSearch(ExploreRecentSearchEntity(keyword = keyword, type = type))

        if (getCountExploreRecentSearch(type) > MAX_RECENT_SEARCHES) {
            deleteOldestSearchByType(type)
        }
    }
    companion object {
        private const val MAX_RECENT_SEARCHES = 6
    }
}
