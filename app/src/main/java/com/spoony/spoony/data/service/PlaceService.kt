package com.spoony.spoony.data.service

import com.spoony.spoony.data.dto.response.BaseResponse
import com.spoony.spoony.data.dto.response.SearchPlaceResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {
    @GET("api/v1/place/search")
    suspend fun getPlaces(
        @Query("query") query: String,
        @Query("display") display: Int = 5
    ): BaseResponse<SearchPlaceResponseDto>
}
