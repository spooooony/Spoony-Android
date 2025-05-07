package com.spoony.spoony.presentation.explore.model

import com.spoony.spoony.domain.entity.CategoryEntity
import com.spoony.spoony.domain.entity.PlaceReviewEntity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

data class PlaceReviewModel(
    val reviewId: Int,
    val userId: Int,
    val userName: String,
    val userRegion: String,
    val description: String,
    val photoUrlList: ImmutableList<String> = persistentListOf(),
    val placeName: String,
    val placeAddress: String,
    val category: CategoryEntity,
    val addMapCount: Int,
    val createdAt: String
)

fun PlaceReviewEntity.toModel(): PlaceReviewModel = PlaceReviewModel(
    reviewId = this.reviewId,
    userId = this.userId,
    userName = this.userName ?: "",
    userRegion = this.userRegion ?: "",
    description = this.description,
    photoUrlList = this.photoUrlList?.toImmutableList() ?: persistentListOf(),
    placeName = this.placeName ?: "",
    placeAddress = this.placeAddress ?: "",
    category = this.category ?: CategoryEntity(
        categoryId = 0,
        categoryName = "",
        iconUrl = "",
        unSelectedIconUrl = "",
        textColor = "",
        backgroundColor = ""
    ),
    addMapCount = this.addMapCount ?: 0,
    createdAt = this.createdAt ?: ""
)
