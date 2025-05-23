package com.spoony.spoony.data.datasourceimpl

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.datasource.RegionRemoteDataSource
import com.spoony.spoony.data.dto.response.RegionListResponseDto
import com.spoony.spoony.data.service.RegionService
import javax.inject.Inject

class RegionRemoteDataSourceImpl @Inject constructor(
    private val regionService: RegionService
) : RegionRemoteDataSource {
    override suspend fun getRegionList(): BaseResponse<RegionListResponseDto> =
        regionService.getRegionList()
}
