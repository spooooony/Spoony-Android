package com.spoony.spoony.presentation.gourmet.map.model

import com.spoony.spoony.domain.entity.LocationEntity

data class LocationModel(
    val placeId: Int? = null,
    val placeName: String? = null,
    val locationAddress: String? = null,
    val scale: Double = 14.0,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)

fun LocationEntity.toModel(): LocationModel = LocationModel(
    placeId = this.locationId,
    placeName = this.locationName,
    locationAddress = this.locationAddress,
    scale = this.scope,
    latitude = this.latitude,
    longitude = this.longitude
)
