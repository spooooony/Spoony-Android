package com.spoony.spoony.domain.entity

data class AddedPlaceListEntity(
    val count: Int,
    val placeList: List<AddedPlaceEntity>
)

data class AddedPlaceEntity(
    val placeId: Int,
    val placeName: String,
    val placeAddress: String,
    val postTitle: String,
    val photoUrl: String,
    val latitude: Double,
    val longitude: Double,
    val categoryInfo: CategoryEntity
)
