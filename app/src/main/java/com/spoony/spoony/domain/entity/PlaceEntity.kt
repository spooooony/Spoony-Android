package com.spoony.spoony.domain.entity

/**
 * 가게 정보
 */

data class PlaceEntity(
    val placeName: String,
    val placeAddress: String,
    val placeRoadAddress: String,
    val latitude: Double,
    val longitude: Double
)
