package com.spoony.spoony.domain.entity

data class UserEntity(
    val userId: Int,
    val userName: String,
    val userProfileUrl: String,
    val userRegion: String
)
