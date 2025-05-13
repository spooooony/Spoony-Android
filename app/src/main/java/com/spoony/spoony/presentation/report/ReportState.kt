package com.spoony.spoony.presentation.report

import com.spoony.spoony.presentation.report.type.ReportOption
import com.spoony.spoony.presentation.report.type.ReportOptionSelector
import kotlinx.collections.immutable.ImmutableList

data class ReportState(
    val reportOptions: ImmutableList<ReportOption> = ReportOptionSelector.getOptionsForType(ReportType.POST),
    val selectedReportOption: ReportOption = ReportOption.ADVERTISEMENT,
    val reportContext: String = "",
    val reportButtonEnabled: Boolean = false,
    val reportType: ReportType = ReportType.USER,
    val targetText: String = "후기"
)

enum class ReportType {
    POST,
    USER
}
