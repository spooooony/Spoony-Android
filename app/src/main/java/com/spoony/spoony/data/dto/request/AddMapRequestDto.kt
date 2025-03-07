package com.spoony.spoony.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddMapRequestDto(
    @SerialName("postId")
    val postId: Int
)
