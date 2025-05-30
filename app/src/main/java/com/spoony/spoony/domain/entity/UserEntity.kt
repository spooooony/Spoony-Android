package com.spoony.spoony.domain.entity

/**
 * 유저 정보
 */

data class UserEntity(
    val userId: Int,
    val userName: String,
    val userProfileUrl: String,
    val userRegion: String?,
    val platform: String? = "KAKAO",
    val platformId: String? = "",
    val reviewCount: Int? = 0,
    val followerCount: Int? = 0,
    val followingCount: Int? = 0,
    val isFollowing: Boolean? = false,
    val introduction: String? = "",
    val createdAt: String? = "",
    val updatedAt: String? = ""
)
