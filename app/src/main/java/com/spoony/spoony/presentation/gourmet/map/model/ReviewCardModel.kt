package com.spoony.spoony.presentation.gourmet.map.model

import com.spoony.spoony.domain.entity.PlaceReviewEntity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

data class ReviewCardModel(
    val placeId: Int,
    val reviewId: Int,
    val userName: String,
    val userRegion: String,
    val placeName: String,
    val description: String,
    val photoUrl: ImmutableList<String>,
    val categoryInfo: CategoryModel,
    val addMapCount: Int
)

fun PlaceReviewEntity.toReviewCardModel(): ReviewCardModel = ReviewCardModel(
    placeId = this.placeId ?: 0,
    reviewId = this.reviewId ?: 0,
    userName = this.userName ?: "",
    userRegion = this.userRegion ?: "",
    placeName = this.placeName ?: "",
    description = this.description,
    photoUrl = this.photoUrlList?.toImmutableList() ?: persistentListOf(),
    categoryInfo = this.category?.toModel() ?: CategoryModel(),
    addMapCount = this.addMapCount ?: 0
)
