package com.spoony.spoony.domain.entity

data class BasicUserInfoEntity(
    val userId: Int,
    val platform: String,
    val platformId: String,
    val userName: String,
    val regionName: String,
    val introduction: String,
    val createdAt: String,
    val updatedAt: String,
    val followerCount: Int,
    val followingCount: Int,
    val isFollowing: Boolean,
    val reviewCount: Int,
    val profileImageUrl: String
)
