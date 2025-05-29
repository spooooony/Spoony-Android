package com.spoony.spoony.data.mapper

import com.spoony.spoony.data.dto.response.AddedMapPostDto
import com.spoony.spoony.domain.entity.PlaceReviewEntity

fun AddedMapPostDto.toDomain() = PlaceReviewEntity(
    placeId = this.placeId,
    reviewId = this.postId,
    placeName = this.placeName,
    category = this.categoryColorResponse.toDomain(),
    userName = this.authorName,
    userRegion = this.authorRegionName,
    description = this.description,
    photoUrlList = this.photoUrlList,
    addMapCount = this.zzimCount
)
