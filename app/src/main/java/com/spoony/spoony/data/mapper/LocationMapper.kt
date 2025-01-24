package com.spoony.spoony.data.mapper

import com.spoony.spoony.data.dto.response.LocationListResponseDto
import com.spoony.spoony.domain.entity.LocationEntity

fun LocationListResponseDto.LocationResponseDto.toDomain(): LocationEntity = LocationEntity(
    locationId = this.locationId,
    locationName = this.locationName,
    locationAddress = this.locationAddress,
    scope = this.locationType.scope,
    latitude = this.latitude,
    longitude = this.longitude
)
