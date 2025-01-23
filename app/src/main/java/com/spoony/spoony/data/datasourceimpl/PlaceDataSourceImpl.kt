package com.spoony.spoony.data.datasourceimpl

import com.spoony.spoony.data.datasource.PlaceDataSource
import com.spoony.spoony.data.dto.base.BaseResponse
import com.spoony.spoony.data.dto.request.PlaceCheckRequestDto
import com.spoony.spoony.data.dto.response.PlaceCheckResponseDto
import com.spoony.spoony.data.dto.response.SearchPlaceResponseDto
import com.spoony.spoony.data.service.PlaceService
import javax.inject.Inject

class PlaceDataSourceImpl @Inject constructor(
    private val placeService: PlaceService
) : PlaceDataSource {
    override suspend fun getPlaces(
        query: String,
        display: Int
    ): BaseResponse<SearchPlaceResponseDto> =
        placeService.getPlaces(query, display)

    override suspend fun checkDuplicatePlace(
        userId: Int,
        latitude: Double,
        longitude: Double
    ): BaseResponse<PlaceCheckResponseDto> =
        placeService.postDuplicatePlace(
            PlaceCheckRequestDto(
                userId = userId,
                latitude = latitude,
                longitude = longitude
            )
        )
}
