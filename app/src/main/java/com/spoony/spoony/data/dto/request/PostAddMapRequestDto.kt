package com.spoony.spoony.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostAddMapRequestDto(
    @SerialName("postId")
    val postId: Int,
    @SerialName("userId")
    val userId: Int
)
