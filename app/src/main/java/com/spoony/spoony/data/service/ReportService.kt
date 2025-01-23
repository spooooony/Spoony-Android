package com.spoony.spoony.data.service

import com.spoony.spoony.data.dto.base.BaseResponse
import com.spoony.spoony.data.dto.request.ReportPostRequestDto
import retrofit2.http.Body
import retrofit2.http.POST

interface ReportService {
    @POST("/api/v1/report")
    suspend fun postReportPost(
        @Body reportPostRequestDto: ReportPostRequestDto
    ): BaseResponse<Boolean>
}
