package com.spoony.spoony.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpoonDrawListReponseDto(
    @SerialName("spoonDrawResponseDTOList")
    val spoonDrawList: List<SpoonDrawResponseDto>,
    @SerialName("spoonBalance")
    val totalSpoonCount: Int,
    @SerialName("weeklyBalance")
    val weeklySpoonCount: Int
)
