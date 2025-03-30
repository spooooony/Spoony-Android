package com.spoony.spoony.data.mapper

import com.spoony.spoony.data.dto.response.AddedMapListResponseDto
import com.spoony.spoony.data.dto.response.AddedMapResponseDto
import com.spoony.spoony.domain.entity.AddedPlaceEntity
import com.spoony.spoony.domain.entity.AddedPlaceListEntity

fun AddedMapListResponseDto.toDomain(): AddedPlaceListEntity = AddedPlaceListEntity(
    count = this.count,
    placeList = this.zzimCardResponses.map { it.toDomain() }
)

fun AddedMapResponseDto.toDomain(): AddedPlaceEntity =
    AddedPlaceEntity(
        placeId = this.placeId,
        placeName = this.placeName,
        placeAddress = this.placeAddress,
        photoUrl = this.photoUrlList,
        latitude = this.latitude,
        longitude = this.longitude,
        categoryInfo = this.categoryColorResponse.toDomain()
    )
