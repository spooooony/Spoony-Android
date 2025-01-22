package com.spoony.spoony.data.datasource

import com.spoony.spoony.data.dto.response.BaseResponse
import com.spoony.spoony.data.dto.response.SearchPlaceData

interface PlaceDataSource {
    suspend fun searchPlace(query: String, display: Int): BaseResponse<SearchPlaceData>
}
