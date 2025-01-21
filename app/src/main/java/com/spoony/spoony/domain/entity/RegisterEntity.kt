package com.spoony.spoony.domain.entity

import java.io.File

data class RegisterEntity(
    val id: String = "",
    val title: String,
    val description: String,
    val placeName: String,
    val placeAddress: String,
    val placeRoadAddress: String,
    val latitude: Double,
    val longitude: Double,
    val categoryId: Int,
    val menuList: List<String>,
    val photos: List<File>
)
