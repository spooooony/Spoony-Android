package com.spoony.spoony.domain.entity

/**
 * mapper 생성 후 삭제 부탁합니다~
 */

data class AddedPlaceListEntity(
    val count: Int,
    val placeList: List<AddedPlaceEntity>
)

data class AddedPlaceEntity(
    val placeId: Int,
    val placeName: String,
    val placeAddress: String,
    val photoUrl: String,
    val latitude: Double,
    val longitude: Double,
    val categoryInfo: CategoryEntity
)
