package com.spoony.spoony.domain.entity

data class PlaceEntity(
    val placeName: String,
    val placeAddress: String,
    val placeRoadAddress: String,
    val latitude: Double,
    val longitude: Double
)

data class PlaceSearchResultEntity(
    val placeList: List<PlaceEntity>
)
