package com.spoony.spoony.presentation.report

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class ReportState(
    val reportOptions: ImmutableList<ReportOption> = ReportOption.entries.toImmutableList(),
    var selectedReportOption: ReportOption = ReportOption.ADVERTISEMENT,
    val reportContext: String = ""
)
