package com.spoony.spoony.data.datasource

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.dto.response.SpoonDrawListReponseDto
import com.spoony.spoony.data.dto.response.SpoonDrawResponseDto

interface SpoonDataSource {
    suspend fun drawSpoon(): BaseResponse<SpoonDrawResponseDto>
    suspend fun getWeeklySpoonDraw(): BaseResponse<SpoonDrawListReponseDto>
}
