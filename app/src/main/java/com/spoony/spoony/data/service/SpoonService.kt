package com.spoony.spoony.data.service

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.dto.response.SpoonDrawResponseDto
import retrofit2.http.POST

interface SpoonService {
    @POST("api/v1/spoon/draw")
    suspend fun spoonDraw(): BaseResponse<SpoonDrawResponseDto>
}
