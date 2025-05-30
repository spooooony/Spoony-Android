package com.spoony.spoony.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileImageResponseDto(
    @SerialName("images")
    val images: List<ImageResponseDto>
)

@Serializable
data class ImageResponseDto(
    @SerialName("imageLevel")
    val imageLevel: Int,
    @SerialName("spoonName")
    val spoonName: String,
    @SerialName("unlockCondition")
    val unlockCondition: String,
    @SerialName("imageUrl")
    val imageUrl: String,
    @SerialName("isUnlocked")
    val isUnlocked: Boolean
)
