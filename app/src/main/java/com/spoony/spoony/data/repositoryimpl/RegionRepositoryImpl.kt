package com.spoony.spoony.data.repositoryimpl

import com.spoony.spoony.data.datasource.RegionRemoteDataSource
import com.spoony.spoony.data.mapper.toDomain
import com.spoony.spoony.domain.entity.RegionEntity
import com.spoony.spoony.domain.repository.RegionRepository
import javax.inject.Inject

class RegionRepositoryImpl @Inject constructor(
    private val regionRemoteDataSource: RegionRemoteDataSource
) : RegionRepository {
    override suspend fun getRegionList(): Result<List<RegionEntity>> =
        runCatching {
            regionRemoteDataSource.getRegionList().data!!.regionList.map { it.toDomain() }
        }
}
