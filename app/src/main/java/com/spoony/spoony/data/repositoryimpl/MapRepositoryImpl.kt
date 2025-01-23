package com.spoony.spoony.data.repositoryimpl

import com.spoony.spoony.data.datasource.PostRemoteDataSource
import com.spoony.spoony.data.mapper.toDomain
import com.spoony.spoony.domain.entity.AddedPlaceListEntity
import com.spoony.spoony.domain.entity.LocationEntity
import com.spoony.spoony.domain.repository.MapRepository
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(
    private val postRemoteDataSource: PostRemoteDataSource
) : MapRepository {
    override suspend fun searchLocation(query: String): Result<List<LocationEntity>> = Result.success(
        listOf(
            LocationEntity(
                locationId = 1,
                locationName = "강남구",
                locationAddress = "서울특별시 강남구"
            ),
            LocationEntity(
                locationId = 2,
                locationName = "종로구",
                locationAddress = "서울특별시 종로구"
            ),
            LocationEntity(
                locationId = 3,
                locationName = "중구",
                locationAddress = "서울특별시 중구"
            )
        )
    )

    override suspend fun getAddedPlaceList(userId: Int): Result<AddedPlaceListEntity> =
        runCatching {
            postRemoteDataSource.getAddedMap(userId).data!!.toDomain()
        }
}
