package com.spoony.spoony.domain.entity

data class UserPageReviewEntity(
    val feedList: List<UserFeedEntity>
)

data class UserFeedEntity(
    val userId: Int,
    val userName: String,
    val userRegion: String,
    val postId: Int,
    val description: String,
    val categoryInfo: CategoryEntity,
    val zzimCount: Int,
    val photoUrlList: List<String>,
    val createdAt: String,
    val isMine: Boolean
)
