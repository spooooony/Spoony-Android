package com.spoony.spoony.domain.repository

interface ReportRepository {
    suspend fun postReportPost(postId: Int, userId: Int, reportType: String, reportDetail: String): Result<Boolean>
}
