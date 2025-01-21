package com.spoony.spoony.domain.entity

data class FeedEntity(
    val userId: Int,
    val userName: String,
    val userRegion: String,
    val postId: Int,
    val title: String,
    val categoryInfo: CategoryEntity,
    val zzimCount: Int
)
