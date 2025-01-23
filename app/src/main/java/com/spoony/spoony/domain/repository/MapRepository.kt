package com.spoony.spoony.domain.repository

import com.spoony.spoony.domain.entity.AddedPlaceEntity
import com.spoony.spoony.domain.entity.LocationEntity

interface MapRepository {
    suspend fun searchLocation(query: String): Result<List<LocationEntity>>

    suspend fun getAddedPlaceList(userId: Int): Result<List<AddedPlaceEntity>>

    suspend fun getAddedPlaceListByLocation(userId: Int, locationId: Int): Result<List<AddedPlaceEntity>>
}
