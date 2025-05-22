package com.spoony.spoony.data.datasource

import com.spoony.spoony.core.network.BaseResponse

interface ReportDataSource {
    suspend fun postReport(postId: Int, reportType: String, reportDetail: String): BaseResponse<Boolean>
    suspend fun userReport(userTargetId: Int, userReportType: String, reportDetail: String): BaseResponse<Boolean>
}
