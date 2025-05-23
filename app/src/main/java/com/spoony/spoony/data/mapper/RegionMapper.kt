package com.spoony.spoony.data.mapper

import com.spoony.spoony.data.dto.response.RegionResponseDto
import com.spoony.spoony.domain.entity.RegionEntity

fun RegionResponseDto.toDomain(): RegionEntity = RegionEntity(
    regionId = this.regionId,
    regionName = this.regionName
)
