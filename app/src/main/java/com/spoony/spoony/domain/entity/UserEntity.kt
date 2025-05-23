package com.spoony.spoony.domain.entity

/**
 * 유저 정보
 */

data class UserEntity(
    val userId: Int,
    val userName: String,
    val userProfileUrl: String,
    val regionName: String,
    val platform: String? = "KAKAO"
)
