package com.spoony.spoony.domain.repository

import com.spoony.spoony.domain.entity.AddedPlaceEntity
import com.spoony.spoony.domain.entity.AddedPlaceListEntity
import com.spoony.spoony.domain.entity.LocationEntity

interface MapRepository {
    suspend fun searchLocation(query: String): Result<List<LocationEntity>>
    suspend fun getAddedPlaceListByLocation(locationId: Int): Result<List<AddedPlaceEntity>>
    suspend fun getAddedPlaceList(categoryId: Int): Result<AddedPlaceListEntity>
    suspend fun getRecentSearches(): Result<List<String>>
    suspend fun deleteSearchByText(searchText: String): Result<Unit>
    suspend fun deleteAllSearches(): Result<Unit>
    suspend fun addSearch(searchText: String): Result<Unit>
}
