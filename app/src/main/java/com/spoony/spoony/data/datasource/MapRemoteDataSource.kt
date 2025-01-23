package com.spoony.spoony.data.datasource

import com.spoony.spoony.data.dto.base.BaseResponse
import com.spoony.spoony.data.dto.response.LocationListResponseDto

interface MapRemoteDataSource {
    suspend fun searchLocation(query: String): BaseResponse<LocationListResponseDto>
}
