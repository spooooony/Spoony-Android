package com.spoony.spoony.domain.repository

interface ReportRepository {
    suspend fun postReport(postId: Int, reportType: String, reportDetail: String): Result<Boolean>
    suspend fun userReport(userTargetId: Int, userReportType: String, reportDetail: String): Result<Boolean>
}
