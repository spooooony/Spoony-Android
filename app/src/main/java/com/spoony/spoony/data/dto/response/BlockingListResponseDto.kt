package com.spoony.spoony.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BlockingListResponseDto(
    @SerialName("users")
    val users: List<User>
)
