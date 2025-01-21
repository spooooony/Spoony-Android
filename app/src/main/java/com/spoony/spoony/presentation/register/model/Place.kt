package com.spoony.spoony.presentation.register.model

import androidx.compose.runtime.Stable

@Stable
data class Place(
    val placeName: String,
    val placeAddress: String,
    val placeRoadAddress: String,
    val latitude: Double,
    val longitude: Double
)
