package com.spoony.spoony.presentation.splash.model

import com.spoony.spoony.domain.entity.BasicUserInfoEntity

data class UserModel(
    val userId: Int,
    val platform: String,
    val userName: String,
    val regionName: String?,
    val introduction: String?,
    val createdAt: String,
    val updatedAt: String,
    val followerCount: Int,
    val followingCount: Int,
    val reviewCount: Int,
    val lastEnteredDate: String?
)

internal fun BasicUserInfoEntity.toModel(lastEnteredDate: String?) = UserModel(
    userId = this.userId,
    platform = this.platform,
    userName = this.userName,
    regionName = this.regionName,
    introduction = this.introduction,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    followerCount = this.followerCount,
    followingCount = this.followingCount,
    reviewCount = this.reviewCount,
    lastEnteredDate = lastEnteredDate
)