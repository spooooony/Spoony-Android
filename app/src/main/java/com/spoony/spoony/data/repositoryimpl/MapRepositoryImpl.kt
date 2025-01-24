package com.spoony.spoony.data.repositoryimpl

import com.spoony.spoony.core.database.SearchDao
import com.spoony.spoony.core.database.entity.SearchEntity
import com.spoony.spoony.data.datasource.MapRemoteDataSource
import com.spoony.spoony.data.datasource.PostRemoteDataSource
import com.spoony.spoony.data.mapper.toDomain
import com.spoony.spoony.domain.entity.AddedPlaceEntity
import com.spoony.spoony.domain.entity.AddedPlaceListEntity
import com.spoony.spoony.domain.entity.LocationEntity
import com.spoony.spoony.domain.repository.MapRepository
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(
    private val mapRemoteDataSource: MapRemoteDataSource,
    private val postRemoteDataSource: PostRemoteDataSource,
    private val searchDao: SearchDao,
) : MapRepository {
    override suspend fun searchLocation(query: String): Result<List<LocationEntity>> =
        runCatching {
            mapRemoteDataSource.searchLocation(query).data!!.locationResponseList.map {
                it.toDomain()
            }
        }

    override suspend fun getAddedPlaceList(userId: Int): Result<AddedPlaceListEntity> =
        runCatching {
            postRemoteDataSource.getAddedMap(userId).data!!.toDomain()
        }

    override suspend fun getAddedPlaceListByLocation(userId: Int, locationId: Int): Result<List<AddedPlaceEntity>> =
        runCatching {
            postRemoteDataSource.getZzimByLocation(userId, locationId)
                .data!!.zzimCardResponses.map { it.toDomain() }
        }

    override suspend fun getRecentSearches(): Result<List<String>> =
        runCatching {
            searchDao.getRecentSearches().map { it.text }
        }

    override suspend fun deleteSearchByText(searchText: String): Result<Unit> =
        runCatching {
            searchDao.deleteSearchByText(searchText)
        }

    override suspend fun deleteAllSearches(): Result<Unit> =
        runCatching {
            searchDao.deleteAllSearches()
        }

    override suspend fun addSearch(searchText: String) =
        runCatching {
            searchDao.addSearchWithLimit(searchText)
        }
}
