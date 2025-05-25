package com.spoony.spoony.data.mapper

import com.spoony.spoony.data.dto.response.BasicUserInfoResponseDto
import com.spoony.spoony.domain.entity.BasicUserInfoEntity

fun BasicUserInfoResponseDto.toDomain() = BasicUserInfoEntity(
    userId = this.userId,
    platform = this.platform,
    platformId = this.platformId,
    userName = this.userName,
    regionName = this.regionName ?: "",
    introduction = this.introduction,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    followerCount = this.followerCount,
    followingCount = this.followingCount,
    isFollowing = this.isFollowing,
    reviewCount = this.reviewCount,
    profileImageUrl = this.profileImageUrl
)
