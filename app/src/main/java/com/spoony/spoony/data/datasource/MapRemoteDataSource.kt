package com.spoony.spoony.data.datasource

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.dto.response.LocationListResponseDto

interface MapRemoteDataSource {
    suspend fun searchLocation(query: String): BaseResponse<LocationListResponseDto>
}
