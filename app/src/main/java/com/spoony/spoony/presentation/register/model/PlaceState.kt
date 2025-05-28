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
) {
    companion object {
        fun empty(): PlaceState = PlaceState(
            placeName = "",
            placeAddress = "",
            placeRoadAddress = "",
            latitude = 0.0,
            longitude = 0.0
        )
    }
}

fun PlaceEntity.toModel(): PlaceState =
    PlaceState(
        placeName = placeName,
        placeAddress = placeAddress,
        placeRoadAddress = placeRoadAddress,
        latitude = latitude,
        longitude = longitude
    )
