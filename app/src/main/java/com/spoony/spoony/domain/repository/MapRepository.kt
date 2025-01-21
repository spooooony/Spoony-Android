package com.spoony.spoony.domain.repository

import com.spoony.spoony.domain.entity.LocationEntity

interface MapRepository {
    suspend fun searchLocation(query: String): Result<List<LocationEntity>>
}
