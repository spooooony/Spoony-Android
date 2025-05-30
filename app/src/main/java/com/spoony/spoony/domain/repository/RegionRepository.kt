package com.spoony.spoony.domain.repository

import com.spoony.spoony.domain.entity.RegionEntity

interface RegionRepository {
    suspend fun getRegionList(): Result<List<RegionEntity>>
}
