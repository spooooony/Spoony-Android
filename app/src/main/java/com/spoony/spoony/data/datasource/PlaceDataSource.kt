package com.spoony.spoony.data.datasource

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.dto.response.PlaceCheckResponseDto
import com.spoony.spoony.data.dto.response.SearchPlaceResponseDto

interface PlaceDataSource {
    suspend fun getPlaces(query: String, display: Int): BaseResponse<SearchPlaceResponseDto>

    suspend fun checkDuplicatePlace(
        latitude: Double,
        longitude: Double
    ): BaseResponse<PlaceCheckResponseDto>
}
