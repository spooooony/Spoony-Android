package com.spoony.spoony.data.service

import com.spoony.spoony.data.dto.request.PlaceCheckRequestDto
import com.spoony.spoony.data.dto.response.BaseResponse
import com.spoony.spoony.data.dto.response.PlaceCheckResponseDto
import com.spoony.spoony.data.dto.response.SearchPlaceResponseDto
import retrofit2.http.Body
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
    suspend fun postDuplicatePlace(
        @Body request: PlaceCheckRequestDto
    ): BaseResponse<PlaceCheckResponseDto>
}
