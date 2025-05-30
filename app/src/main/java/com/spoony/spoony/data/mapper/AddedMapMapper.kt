package com.spoony.spoony.data.mapper

import com.spoony.spoony.data.dto.response.AddedMapResponseDto
import com.spoony.spoony.domain.entity.PlaceReviewEntity

fun AddedMapResponseDto.toDomain(): PlaceReviewEntity =
    PlaceReviewEntity(
        placeId = this.placeId,
        placeName = this.placeName,
        placeAddress = this.placeAddress,
        description = this.description,
        photoUrlList = listOf(this.photoUrl),
        latitude = this.latitude,
        longitude = this.longitude,
        category = this.categoryColorResponse.toDomain()
    )
