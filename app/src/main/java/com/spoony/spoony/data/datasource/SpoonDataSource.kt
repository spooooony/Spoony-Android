package com.spoony.spoony.data.datasource

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.dto.response.SpoonDrawListReponseDto
import com.spoony.spoony.data.dto.response.SpoonDrawResponseDto
import com.spoony.spoony.data.dto.response.UserSpoonCountResponseDto

interface SpoonDataSource {
    suspend fun drawSpoon(): BaseResponse<SpoonDrawResponseDto>
    suspend fun getWeeklySpoonDraw(): BaseResponse<SpoonDrawListReponseDto>
    suspend fun getSpoonCount(): BaseResponse<UserSpoonCountResponseDto>
}
