package com.spoony.spoony.data.service

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.dto.request.ReportPostRequestDto
import com.spoony.spoony.data.dto.request.ReportUserRequestDto
import retrofit2.http.Body
import retrofit2.http.POST

interface ReportService {
    @POST("/api/v1/report/post")
    suspend fun postReport(
        @Body reportPostRequestDto: ReportPostRequestDto
    ): BaseResponse<Boolean>

    @POST("/api/v1/report/user")
    suspend fun userReport(
        @Body reportUserRequestDto: ReportUserRequestDto
    ): BaseResponse<Boolean>
}
