package com.spoony.spoony.data.service

import com.spoony.spoony.data.dto.base.BaseResponse
import com.spoony.spoony.data.dto.response.LocationListResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MapService {
    @GET("api/v1/location/search")
    suspend fun searchLocation(
        @Query("query") query: String
    ): BaseResponse<LocationListResponseDto>
}
