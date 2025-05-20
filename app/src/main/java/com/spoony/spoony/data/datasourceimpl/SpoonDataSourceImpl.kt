package com.spoony.spoony.data.datasourceimpl

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.datasource.SpoonDataSource
import com.spoony.spoony.data.dto.response.SpoonDrawListReponseDto
import com.spoony.spoony.data.dto.response.SpoonDrawResponseDto
import com.spoony.spoony.data.service.SpoonService
import javax.inject.Inject

class SpoonDataSourceImpl @Inject constructor(
    private val spoonService: SpoonService
) : SpoonDataSource {
    override suspend fun drawSpoon(): BaseResponse<SpoonDrawResponseDto> = spoonService.spoonDraw()
    override suspend fun getWeeklySpoonDraw(): BaseResponse<SpoonDrawListReponseDto> =
        spoonService.getWeeklySpoonDraw()
}
