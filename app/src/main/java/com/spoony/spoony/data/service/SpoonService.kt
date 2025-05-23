package com.spoony.spoony.data.service

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.dto.response.SpoonDrawListReponseDto
import com.spoony.spoony.data.dto.response.SpoonDrawResponseDto
import com.spoony.spoony.data.dto.response.UserSpoonCountResponseDto
import retrofit2.http.GET
import retrofit2.http.POST

interface SpoonService {
    @POST("api/v1/spoon/draw")
    suspend fun spoonDraw(): BaseResponse<SpoonDrawResponseDto>

    @GET("api/v1/spoon/draw")
    suspend fun getWeeklySpoonDraw(): BaseResponse<SpoonDrawListReponseDto>

    @GET("/api/v1/spoon")
    suspend fun getSpoonCount(): BaseResponse<UserSpoonCountResponseDto>
}
