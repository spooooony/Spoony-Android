package com.spoony.spoony.presentation.register.model

import com.spoony.spoony.domain.entity.CategoryEntity
import com.spoony.spoony.domain.entity.PlaceReviewEntity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class PlaceReviewModel(
    val reviewId: Int,
    val userId: Int,
    val photoUrls: ImmutableList<String>,
    val date: String,
    val menuList: ImmutableList<String>,
    val description: String,
    val value: Double,
    val cons: String?,
    val placeName: String,
    val placeAddress: String,
    val latitude: Double,
    val longitude: Double,
    val category: CategoryModel
)

data class CategoryModel(
    val categoryId: Int,
    val categoryName: String,
    val iconUrl: String
)

fun PlaceReviewEntity.toModel(): PlaceReviewModel =
    PlaceReviewModel(
        reviewId = reviewId,
        userId = userId,
        photoUrls = (photoUrlList ?: emptyList()).toImmutableList(),
        date = createdAt ?: "",
        menuList = (menuList ?: emptyList()).toImmutableList(),
        description = description,
        value = value ?: 0.0,
        cons = cons,
        placeName = placeName ?: "",
        placeAddress = placeAddress ?: "",
        latitude = latitude ?: 0.0,
        longitude = longitude ?: 0.0,
        category = category?.toModel() ?: CategoryModel(0, "", "")
    )

fun CategoryEntity.toModel(): CategoryModel =
    CategoryModel(
        categoryId = categoryId,
        categoryName = categoryName,
        iconUrl = iconUrl
    )
