package com.spoony.spoony.presentation.report

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.immutableListOf

data class ReportState(
    val reportOptions: ImmutableList<ReportOption> = immutableListOf(*ReportOption.entries.toTypedArray()),
    var selectedReportOption: ReportOption = ReportOption.COMMERCIAL,
    val reportContext: String = ""
)
