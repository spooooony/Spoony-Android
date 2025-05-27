package com.spoony.spoony.domain.entity

data class ProfileImageEntity(
    val images: List<ImageEntity>
)

data class ImageEntity(
    val imageLevel: Int,
    val unlockCondition: String,
    val imageUrl: String,
    val isUnlocked: Boolean
)
