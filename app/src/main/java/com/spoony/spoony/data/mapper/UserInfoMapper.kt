package com.spoony.spoony.data.mapper

import com.spoony.spoony.data.dto.response.UserInfoResponseDto
import com.spoony.spoony.data.dto.response.UserSimpleResponseDto
import com.spoony.spoony.domain.entity.UserEntity

fun UserInfoResponseDto.toDomain(): UserEntity = UserEntity(
    userId = this.userId,
    userName = this.userName,
    userProfileUrl = this.userImageUrl,
    regionName = this.regionName,
    platform = this.platform
)

fun UserSimpleResponseDto.toDomain(): UserEntity = UserEntity(
    userId = this.userId,
    userName = this.username,
    userProfileUrl = this.profileImageUrl,
    userRegion = this.regionName
)
