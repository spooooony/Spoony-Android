package com.spoony.spoony.core.database.entity

import androidx.room.Entity

@Entity(
    tableName = "explore_recent_search",
    primaryKeys = ["keyword", "type"]
)
data class ExploreRecentSearchEntity(
    val type: ExploreRecentSearchType,
    val keyword: String,
    val timestamp: Long = System.currentTimeMillis()
)

enum class ExploreRecentSearchType {
    USER, REVIEW
}
