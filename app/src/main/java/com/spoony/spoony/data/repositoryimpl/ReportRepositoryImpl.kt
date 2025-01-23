package com.spoony.spoony.data.repositoryimpl

import com.spoony.spoony.data.datasource.ReportDataSource
import com.spoony.spoony.domain.repository.ReportRepository
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val reportDataSource: ReportDataSource
) : ReportRepository {
    override suspend fun postReportPost(postId: Int, userId: Int, reportType: String, reportDetail: String): Result<Boolean> =
        runCatching {
            reportDataSource.postReportPost(postId = postId, userId = userId, reportType = reportType, reportDetail = reportDetail).success
        }
}
