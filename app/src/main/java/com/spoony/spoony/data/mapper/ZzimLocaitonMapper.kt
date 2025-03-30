package com.spoony.spoony.data.mapper

import com.spoony.spoony.data.dto.response.ZzimCardResponseDto
import com.spoony.spoony.domain.entity.AddedPlaceEntity
import com.spoony.spoony.domain.entity.CategoryEntity

fun ZzimCardResponseDto.toDomain(): AddedPlaceEntity =
    AddedPlaceEntity(
        placeId = placeId,
        placeName = placeName,
        placeAddress = placeAddress,
        photoUrl = photoUrl,
        latitude = latitude,
        longitude = longitude,
        categoryInfo = CategoryEntity(
            categoryId = categoryColorResponse.categoryId,
            categoryName = categoryColorResponse.categoryName,
            iconUrl = categoryColorResponse.iconUrl,
            textColor = categoryColorResponse.iconTextColor,
            backgroundColor = categoryColorResponse.iconBackgroundColor
        )
    )
