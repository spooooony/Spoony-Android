package com.spoony.spoony.data.mapper

import com.spoony.spoony.data.dto.response.ZzimCardResponseDto
import com.spoony.spoony.domain.entity.PlaceReviewEntity

fun ZzimCardResponseDto.toDomain(): PlaceReviewEntity =
    PlaceReviewEntity(
        placeId = placeId,
        placeName = placeName,
        placeAddress = placeAddress,
        photoUrlList = listOf(photoUrl),
        latitude = latitude,
        longitude = longitude,
        category = this.categoryColorResponse.toDomain(),
        description = this.description
    )
