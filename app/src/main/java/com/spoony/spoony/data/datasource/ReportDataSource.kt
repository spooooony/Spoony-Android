package com.spoony.spoony.data.datasource

import com.spoony.spoony.data.dto.base.BaseResponse

interface ReportDataSource {
    suspend fun postReportPost(postId: Int, userId: Int, reportType: String, reportDetail: String): BaseResponse<Boolean>
}
