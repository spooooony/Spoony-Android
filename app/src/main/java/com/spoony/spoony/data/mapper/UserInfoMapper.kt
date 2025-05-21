package com.spoony.spoony.data.mapper

import com.spoony.spoony.data.dto.response.UserInfoResponseDto
import com.spoony.spoony.domain.entity.UserEntity

fun UserInfoResponseDto.toDomain(): UserEntity = UserEntity(
    userId = this.userId,
    userName = this.userName,
    userProfileUrl = this.profileImageUrl,
    userRegion = this.regionName,
    platform = this.platform,
    platformId = this.platformId,
    introduction = this.introduction,
    followerCount = this.followerCount,
    followingCount = this.followingCount,
    isFollowing = this.isFollowing,
    reviewCount = this.reviewCount,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)
