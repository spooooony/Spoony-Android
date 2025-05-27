package com.spoony.spoony.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "explore_recent_search"
)
data class ExploreRecentSearchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: ExploreRecentSearchType,
    val keyword: String,
    val timestamp: Long = System.currentTimeMillis()
)

enum class ExploreRecentSearchType {
    USER, REVIEW
}
