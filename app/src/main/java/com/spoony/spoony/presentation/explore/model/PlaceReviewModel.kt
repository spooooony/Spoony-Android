package com.spoony.spoony.presentation.explore.model

import com.spoony.spoony.domain.entity.CategoryEntity
import com.spoony.spoony.domain.entity.PlaceReviewEntity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

data class PlaceReviewModel(
    val reviewId: Int,
    val userId: Int,
    val userName: String? = null,
    val userRegion: String? = null,
    val description: String,
    val photoUrlList: ImmutableList<String>? = persistentListOf(),
    val placeName: String? = null,
    val placeAddress: String? = null,
    val category: CategoryEntity? = null,
    val addMapCount: Int? = null,
    val createdAt: String? = null
)

fun PlaceReviewEntity.toModel(): PlaceReviewModel = PlaceReviewModel(
    reviewId = this.reviewId,
    userId = this.userId,
    userName = this.userName,
    userRegion = this.userRegion,
    description = this.description,
    photoUrlList = this.photoUrlList?.toImmutableList(),
    placeName = this.placeName,
    placeAddress = this.placeAddress,
    category = this.category,
    addMapCount = this.addMapCount,
    createdAt = this.createdAt
)
