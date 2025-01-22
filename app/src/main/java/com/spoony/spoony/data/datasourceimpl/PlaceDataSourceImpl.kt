package com.spoony.spoony.data.datasourceimpl

import com.spoony.spoony.data.datasource.PlaceDataSource
import com.spoony.spoony.data.dto.request.PlaceCheckRequestDTO
import com.spoony.spoony.data.dto.response.BaseResponse
import com.spoony.spoony.data.dto.response.PlaceCheckResponseDTO
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
        userId: Long,
        latitude: Double,
        longitude: Double
    ): BaseResponse<PlaceCheckResponseDTO> =
        placeService.checkDuplicatePlace(
            PlaceCheckRequestDTO(
                userId = userId,
                latitude = latitude,
                longitude = longitude
            )
        )

}
