package com.spoony.spoony.data.service

import com.spoony.spoony.data.dto.request.PlaceCheckRequestDTO
import com.spoony.spoony.data.dto.response.BaseResponse
import com.spoony.spoony.data.dto.response.PlaceCheckResponseDTO
import com.spoony.spoony.data.dto.response.SearchPlaceData
import retrofit2.http.Body
import com.spoony.spoony.data.dto.response.SearchPlaceResponseDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PlaceService {
    @GET("api/v1/place/search")
    suspend fun getPlaces(
        @Query("query") query: String,
        @Query("display") display: Int = 5
    ): BaseResponse<SearchPlaceResponseDto>

    @POST("api/v1/place/check")
    suspend fun checkDuplicatePlace(
        @Body request: PlaceCheckRequestDTO
    ): BaseResponse<PlaceCheckResponseDTO>
}
