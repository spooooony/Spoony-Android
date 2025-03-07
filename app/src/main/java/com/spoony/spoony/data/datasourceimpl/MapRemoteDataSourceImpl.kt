package com.spoony.spoony.data.datasourceimpl

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.datasource.MapRemoteDataSource
import com.spoony.spoony.data.dto.response.LocationListResponseDto
import com.spoony.spoony.data.service.MapService
import javax.inject.Inject

class MapRemoteDataSourceImpl @Inject constructor(
    private val mapService: MapService
) : MapRemoteDataSource {
    override suspend fun searchLocation(query: String): BaseResponse<LocationListResponseDto> =
        mapService.searchLocation(query)
}
