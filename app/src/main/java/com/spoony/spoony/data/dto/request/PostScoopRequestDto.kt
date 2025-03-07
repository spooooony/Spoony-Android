package com.spoony.spoony.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostScoopRequestDto(
    @SerialName("postId")
    val postId: Int
)
