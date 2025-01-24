package com.spoony.spoony.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.spoony.spoony.core.database.entity.SearchEntity

@Dao
interface SearchDao {
    // 검색어 추가
    @Insert
    suspend fun insertSearch(searchEntity: SearchEntity)

    // 특정 검색어 삭제
    @Query("DELETE FROM search WHERE text = :searchText")
    suspend fun deleteSearchByText(searchText: String)

    // 전체 검색어 삭제
    @Query("DELETE FROM search")
    suspend fun deleteAllSearches()

    // 최신순으로 최대 6개의 검색어 가져오기
    @Query("SELECT * FROM search ORDER BY id DESC LIMIT 6")
    suspend fun getRecentSearches(): List<SearchEntity>

    // 검색어 추가 시, 최근 6개만 유지하도록 처리
    @Transaction
    suspend fun addSearchWithLimit(searchText: String) {
        // 새 검색어 삽입
        insertSearch(SearchEntity(text = searchText))

        // 6개 초과 검색어 삭제
        val allSearches = getAllSearches()
        if (allSearches.size > 6) {
            val excessSearches = allSearches.drop(6)
            excessSearches.forEach { deleteSearchById(it.id) }
        }
    }

    // 특정 ID 검색어 삭제
    @Query("DELETE FROM search WHERE id = :id")
    suspend fun deleteSearchById(id: Int)

    // 전체 검색어 가져오기
    @Query("SELECT * FROM search ORDER BY id DESC")
    suspend fun getAllSearches(): List<SearchEntity>
}
