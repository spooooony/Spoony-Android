package com.spoony.spoony.presentation.explore.model

import com.spoony.spoony.domain.entity.CategoryEntity
import com.spoony.spoony.domain.entity.FeedEntity

data class FeedModel(
    val feedId: Int,
    val userId: Int,
    val username: String,
    val userRegion: String,
    val title: String,
    val categoryEntity: CategoryEntity,
    val addMapCount: Int
)

fun FeedEntity.toModel(): FeedModel = FeedModel(
    feedId = this.postId,
    userId = this.userId,
    username = this.userName,
    userRegion = this.userRegion,
    title = this.title,
    categoryEntity = this.categoryInfo,
    addMapCount = this.zzimCount
)
