package com.spoony.spoony.data.mapper

import com.spoony.spoony.data.dto.response.GetRegionResponseDto
import com.spoony.spoony.domain.entity.RegionEntity

fun GetRegionResponseDto.toDomain(): RegionEntity = RegionEntity(
    regionId = this.regionId,
    regionName = this.regionName
)
