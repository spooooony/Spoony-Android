package com.spoony.spoony.data.mapper

import com.spoony.spoony.data.dto.response.UserInfoResponseDto
import com.spoony.spoony.domain.entity.UserEntity

fun UserInfoResponseDto.toDomain(): UserEntity = UserEntity(
    userId = this.userId,
    userEmail = this.userEmail,
    userName = this.userName,
    userProfileUrl = this.userImageUrl,
    userRegion = this.regionName
)
