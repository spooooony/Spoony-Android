package com.spoony.spoony.data.mapper

import com.spoony.spoony.data.dto.response.ImageResponseDto
import com.spoony.spoony.data.dto.response.ProfileImageResponseDto
import com.spoony.spoony.domain.entity.ImageEntity
import com.spoony.spoony.domain.entity.ProfileImageEntity

fun ProfileImageResponseDto.toDomain() = ProfileImageEntity(
    images = this.images.map { it.toDomain() }
)

fun ImageResponseDto.toDomain() = ImageEntity(
    imageLevel = this.imageLevel,
    unlockCondition = this.unlockCondition,
    imageUrl = this.imageUrl,
    isUnlocked = this.isUnlocked
)
