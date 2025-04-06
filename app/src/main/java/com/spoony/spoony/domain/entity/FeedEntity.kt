package com.spoony.spoony.domain.entity

/**
 * mapper 생성 후 삭제 부탁합니다~
 */

data class FeedEntity(
    val userId: Int,
    val userName: String,
    val userRegion: String,
    val postId: Int,
    val title: String,
    val categoryInfo: CategoryEntity,
    val zzimCount: Int
)
