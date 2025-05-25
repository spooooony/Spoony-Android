package com.spoony.spoony.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FollowRequestDto(
    @SerialName("targetUserId")
    val targetUserId: Int
)
