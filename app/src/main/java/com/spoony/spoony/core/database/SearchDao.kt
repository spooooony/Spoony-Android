package com.spoony.spoony.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.spoony.spoony.core.database.entity.SearchEntity

@Dao
interface SearchDao {
    @Insert
    suspend fun insertSearch(searchEntity: SearchEntity)

    @Query("DELETE FROM search WHERE text = :searchText")
    suspend fun deleteSearchByText(searchText: String)

    @Query("DELETE FROM search")
    suspend fun deleteAllSearches()

    @Query("SELECT EXISTS(SELECT 1 FROM search WHERE text = :searchText)")
    suspend fun isSearchExists(searchText: String): Boolean

    @Query("DELETE FROM search WHERE id IN (SELECT id FROM search ORDER BY id ASC LIMIT 1)")
    suspend fun deleteOldestSearch()

    @Query("SELECT * FROM search ORDER BY id DESC LIMIT 6")
    suspend fun getRecentSearches(): List<SearchEntity>

    @Query("SELECT COUNT(*) FROM search")
    suspend fun getSearchCount(): Int

    // 검색어 추가 시, 중복 체크 및 최근 6개만 유지하도록 처리
    @Transaction
    suspend fun addSearchWithLimit(searchText: String) {
        // 검색어 정규화 - 앞뒤 공백 제거
        val trimmedText = searchText.trim()

        // 빈 문자열이나 공백만 있는 경우 저장하지 않음
        if (trimmedText.isBlank()) return

        // 이미 존재하는 검색어인 경우 기존 항목 삭제
        if (isSearchExists(trimmedText)) {
            deleteSearchByText(trimmedText)
        }

        // 검색어가 6개 이상인 경우 가장 오래된 검색어 삭제
        if (getSearchCount() >= 6) {
            deleteOldestSearch()
        }

        // 새로운 검색어 추가
        insertSearch(SearchEntity(text = trimmedText))
    }
}
