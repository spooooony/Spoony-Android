package com.spoony.spoony.data.datasourceimpl

import com.spoony.spoony.data.datasource.PlaceDataSource
import com.spoony.spoony.data.dto.response.BaseResponse
import com.spoony.spoony.data.dto.response.SearchPlaceData
import com.spoony.spoony.data.service.PlaceService
import javax.inject.Inject

class PlaceDataSourceImpl @Inject constructor(
    private val placeService: PlaceService
) : PlaceDataSource {
    override suspend fun searchPlace(
        query: String,
        display: Int
    ): BaseResponse<SearchPlaceData> =
        placeService.searchPlace(query, display)
}
