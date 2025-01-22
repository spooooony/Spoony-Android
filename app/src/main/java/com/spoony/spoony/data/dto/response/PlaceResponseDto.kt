package com.spoony.spoony.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchPlaceResponseDto(
    @SerialName("placeList")
    val placeList: List<PlaceResponseDto>
)

@Serializable
data class PlaceResponseDto(
    @SerialName("placeName")
    val placeName: String,
    @SerialName("placeAddress")
    val placeAddress: String,
    @SerialName("placeRoadAddress")
    val placeRoadAddress: String,
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double
)
