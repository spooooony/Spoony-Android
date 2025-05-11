package com.spoony.spoony.domain.entity

/**
 * 후기 정보
 */

data class PlaceReviewEntity(
    val reviewId: Int,
    val userId: Int,
    val userName: String? = null,
    val userRegion: String? = null,
    val description: String,
    val value: Double? = 0.5,
    val cons: String? = null,
    val photoUrlList: List<String>? = listOf(),
    val menuList: List<String>? = listOf(),
    val placeName: String? = null,
    val placeAddress: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val category: CategoryEntity? = null,
    val addMapCount: Int? = null,
    val isMine: Boolean? = null,
    val isAddMap: Boolean? = null,
    val isScooped: Boolean? = null,
    val createdAt: String? = null
)
