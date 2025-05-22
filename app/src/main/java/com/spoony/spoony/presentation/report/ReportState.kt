package com.spoony.spoony.presentation.report

import com.spoony.spoony.presentation.report.type.ReportOption
import com.spoony.spoony.presentation.report.type.ReportOptionSelector
import kotlinx.collections.immutable.ImmutableList

data class ReportState(
    val reportOptions: ImmutableList<ReportOption> = ReportOptionSelector.getOptionsForType(ReportType.POST),
    val selectedReportOption: ReportOption = ReportOption.PROMOTIONAL_CONTENT,
    val reportContext: String = "",
    val reportButtonEnabled: Boolean = false,
    val reportType: ReportType = ReportType.USER
)

enum class ReportType(
    val text: String
) {
    POST(text = "후기"),
    USER(text = "유저")
}
