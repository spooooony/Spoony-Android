package com.spoony.spoony.data.datasource

import com.spoony.spoony.data.dto.response.BaseResponse
import com.spoony.spoony.data.dto.response.SearchPlaceResponseDto

interface PlaceDataSource {
    suspend fun getPlaces(query: String, display: Int): BaseResponse<SearchPlaceResponseDto>
}
