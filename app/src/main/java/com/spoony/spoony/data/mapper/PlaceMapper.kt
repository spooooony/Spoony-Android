package com.spoony.spoony.data.mapper

import com.spoony.spoony.data.dto.response.PlaceDto
import com.spoony.spoony.domain.entity.PlaceEntity
import com.spoony.spoony.presentation.register.model.Place

fun PlaceDto.toPlaceEntity(): PlaceEntity =
    PlaceEntity(
        placeName = placeName,
        placeAddress = placeAddress,
        placeRoadAddress = placeRoadAddress,
        latitude = latitude,
        longitude = longitude
    )

fun PlaceEntity.toPlace(): Place =
    Place(
        placeName = placeName,
        placeAddress = placeAddress,
        placeRoadAddress = placeRoadAddress,
        latitude = latitude,
        longitude = longitude
    )
