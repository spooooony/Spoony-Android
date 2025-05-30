package com.spoony.spoony.data.datasource

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.dto.response.GetRegionListDto

interface RegionRemoteDataSource {
    suspend fun getRegionList(): BaseResponse<GetRegionListDto>
}
