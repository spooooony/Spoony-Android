package com.spoony.spoony.data.mapper

import com.spoony.spoony.data.dto.response.PlaceResponseDto
import com.spoony.spoony.domain.entity.PlaceEntity

fun PlaceResponseDto.toDomain(): PlaceEntity =
    PlaceEntity(
        placeName = placeName,
        placeAddress = placeAddress,
        placeRoadAddress = placeRoadAddress,
        latitude = latitude,
        longitude = longitude
    )
