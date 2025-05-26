package com.spoony.spoony.domain.entity

data class RegisterPostEntity(
    val description: String,
    val value: Float,
    val cons: String?,
    val placeName: String,
    val placeAddress: String,
    val placeRoadAddress: String,
    val latitude: Double,
    val longitude: Double,
    val categoryId: Int,
    val menuList: List<String>,
    val photos: List<String>
)
