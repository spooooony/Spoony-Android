package com.spoony.spoony.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterPostRequestDto(
    @SerialName("description")
    val description: String,
    @SerialName("value")
    val value: Float,
    @SerialName("cons")
    val cons: String?,
    @SerialName("placeName")
    val placeName: String,
    @SerialName("placeAddress")
    val placeAddress: String,
    @SerialName("placeRoadAddress")
    val placeRoadAddress: String,
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("categoryId")
    val categoryId: Int,
    @SerialName("menuList")
    val menuList: List<String>
)
