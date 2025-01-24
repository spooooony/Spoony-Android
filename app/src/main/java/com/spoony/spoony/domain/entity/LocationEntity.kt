package com.spoony.spoony.domain.entity

data class LocationEntity(
    val locationId: Int,
    val locationName: String,
    val locationAddress: String,
    val scope: Double,
    val latitude: Double,
    val longitude: Double
)
