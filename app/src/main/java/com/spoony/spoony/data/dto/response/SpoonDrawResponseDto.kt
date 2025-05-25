package com.spoony.spoony.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpoonDrawResponseDto(
    @SerialName("drawId")
    val drawId: Int,
    @SerialName("spoonType")
    val spoonType: SpoonType,
    @SerialName("localDate")
    val localDate: String,
    @SerialName("weekStartDate")
    val weekStartDate: String,
    @SerialName("createdAt")
    val createdAt: String
) {
    @Serializable
    data class SpoonType(
        @SerialName("spoonTypeId")
        val spoonTypeId: Int,
        @SerialName("spoonName")
        val spoonName: String,
        @SerialName("spoonAmount")
        val spoonAmount: Int,
        @SerialName("probability")
        val probability: Double,
        @SerialName("spoonImage")
        val spoonImage: String
    )
}
