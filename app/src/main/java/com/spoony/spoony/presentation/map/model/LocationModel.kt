package com.spoony.spoony.presentation.map.model

data class LocationModel(
    val placeId: Int? = null,
    val placeName: String? = null,
    val scale: Double = 14.0,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)
