package com.spoony.spoony.data.repositoryimpl

import com.spoony.spoony.data.datasource.ReportDataSource
import com.spoony.spoony.domain.repository.ReportRepository
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val reportDataSource: ReportDataSource
) : ReportRepository {
    override suspend fun postReport(postId: Int, reportType: String, reportDetail: String): Result<Boolean> =
        runCatching {
            reportDataSource.postReport(postId = postId, reportType = reportType, reportDetail = reportDetail).success
        }
    override suspend fun userReport(userTargetId: Int, userReportType: String, reportDetail: String): Result<Boolean> =
        runCatching {
            reportDataSource.userReport(userTargetId = userTargetId, userReportType = userReportType, reportDetail = reportDetail).success
        }
}
