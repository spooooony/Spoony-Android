package com.spoony.spoony.presentation.gourmet.map.model

import com.spoony.spoony.domain.entity.PlaceReviewEntity

data class PlaceReviewModel(
    val placeId: Int,
    val placeName: String,
    val placeAddress: String,
    val description: String,
    val photoUrl: String,
    val latitude: Double,
    val longitude: Double,
    val categoryInfo: CategoryModel
)

fun PlaceReviewEntity.toReviewModel(): PlaceReviewModel = PlaceReviewModel(
    placeId = this.placeId ?: 0,
    placeName = this.placeName ?: "",
    placeAddress = this.placeAddress ?: "",
    description = this.description,
    photoUrl = this.photoUrlList?.get(0) ?: "",
    latitude = this.latitude ?: 0.00,
    longitude = this.longitude ?: 0.00,
    categoryInfo = this.category?.toModel() ?: CategoryModel()
)
