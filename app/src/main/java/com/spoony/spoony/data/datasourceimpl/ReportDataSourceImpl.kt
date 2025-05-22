package com.spoony.spoony.data.datasourceimpl

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.datasource.ReportDataSource
import com.spoony.spoony.data.dto.request.ReportPostRequestDto
import com.spoony.spoony.data.dto.request.ReportUserRequestDto
import com.spoony.spoony.data.service.ReportService
import javax.inject.Inject

class ReportDataSourceImpl @Inject constructor(
    private val reportService: ReportService
) : ReportDataSource {
    override suspend fun postReport(postId: Int, reportType: String, reportDetail: String): BaseResponse<Boolean> =
        reportService.postReport(
            ReportPostRequestDto(postId = postId, reportType = reportType, reportDetail = reportDetail)
        )
    override suspend fun userReport(userTargetId: Int, userReportType: String, reportDetail: String): BaseResponse<Boolean> =
        reportService.userReport(
            ReportUserRequestDto(targetUserId = userTargetId, userReportType = userReportType, reportDetail = reportDetail)
        )
}
