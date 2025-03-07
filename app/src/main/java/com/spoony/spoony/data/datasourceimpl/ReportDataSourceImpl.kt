package com.spoony.spoony.data.datasourceimpl

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.datasource.ReportDataSource
import com.spoony.spoony.data.dto.request.ReportPostRequestDto
import com.spoony.spoony.data.service.ReportService
import javax.inject.Inject

class ReportDataSourceImpl @Inject constructor(
    private val reportService: ReportService
) : ReportDataSource {
    override suspend fun postReportPost(postId: Int, userId: Int, reportType: String, reportDetail: String): BaseResponse<Boolean> =
        reportService.postReportPost(
            ReportPostRequestDto(postId = postId, userId = userId, reportType = reportType, reportDetail = reportDetail)
        )
}
