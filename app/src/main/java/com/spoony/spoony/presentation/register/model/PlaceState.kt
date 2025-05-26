package com.spoony.spoony.presentation.register.model

import androidx.compose.runtime.Stable
import com.spoony.spoony.domain.entity.PlaceEntity

@Stable
data class PlaceState(
    val placeName: String,
    val placeAddress: String,
    val placeRoadAddress: String,
    val latitude: Double,
    val longitude: Double
)

fun PlaceEntity.toPresentation(): PlaceState =
    PlaceState(
        placeName = placeName,
        placeAddress = placeAddress,
        placeRoadAddress = placeRoadAddress,
        latitude = latitude,
        longitude = longitude
    )
