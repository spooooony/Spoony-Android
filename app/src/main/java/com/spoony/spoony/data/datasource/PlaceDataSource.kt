package com.spoony.spoony.data.datasource

import com.spoony.spoony.data.dto.base.BaseResponse
import com.spoony.spoony.data.dto.response.PlaceCheckResponseDto
import com.spoony.spoony.data.dto.response.SearchPlaceResponseDto

interface PlaceDataSource {
    suspend fun getPlaces(query: String, display: Int): BaseResponse<SearchPlaceResponseDto>

    suspend fun checkDuplicatePlace(
        userId: Long,
        latitude: Double,
        longitude: Double
    ): BaseResponse<PlaceCheckResponseDto>
}
