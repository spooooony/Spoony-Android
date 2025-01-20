package com.spoony.spoony.presentation.explore

import com.spoony.spoony.domain.entity.CategoryEntity

data class FeedModel(
    val feedId: Int,
    val userId: Int,
    val username: String,
    val userRegion: String,
    val title: String,
    val categoryEntity: CategoryEntity,
    val addMapCount: Int
)
