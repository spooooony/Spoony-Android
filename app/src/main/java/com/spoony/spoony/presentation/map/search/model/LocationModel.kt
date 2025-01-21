package com.spoony.spoony.presentation.map.search.model

import com.spoony.spoony.domain.entity.LocationEntity

data class LocationModel(
    val locationId: Int,
    val locationName: String,
    val locationAddress: String
)

fun LocationEntity.toModel(): LocationModel = LocationModel(
    locationId = this.locationId,
    locationName = this.locationName,
    locationAddress = this.locationAddress
)
