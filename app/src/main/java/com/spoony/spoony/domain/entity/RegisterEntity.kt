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
) {
    companion object {
        const val MIN_MENU_COUNT = 1
        const val MAX_MENU_COUNT = 3
        const val MIN_PHOTO_COUNT = 1
        const val MAX_PHOTO_COUNT = 5
        const val MIN_DETAIL_REVIEW_LENGTH = 50
        const val MAX_DETAIL_REVIEW_LENGTH = 500
        const val MAX_ONE_LINE_REVIEW_LENGTH = 30
    }
}
