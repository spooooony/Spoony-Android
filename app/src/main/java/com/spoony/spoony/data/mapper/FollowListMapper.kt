package com.spoony.spoony.data.mapper

import com.spoony.spoony.data.dto.response.FollowListResponseDto
import com.spoony.spoony.data.dto.response.User as UserDto
import com.spoony.spoony.domain.entity.FollowListEntity
import com.spoony.spoony.domain.entity.User as UserEntity

fun FollowListResponseDto.toDomain() = FollowListEntity(
    count = this.count,
    users = this.users.map { it.toDomain() }
)

fun UserDto.toDomain() = UserEntity(
    userId = this.userId,
    username = this.username,
    regionName = this.regionName,
    isFollowing = this.isFollowing,
    profileImageUrl = this.profileImageUrl,
    isMe = this.isMe
)
