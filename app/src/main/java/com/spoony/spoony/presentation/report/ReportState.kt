package com.spoony.spoony.presentation.report

import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.presentation.report.type.ReportOption
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class ReportState(
    val postId: UiState<Int> = UiState.Loading,
    val reportOptions: ImmutableList<ReportOption> = ReportOption.entries.toImmutableList(),
    val selectedReportOption: ReportOption = ReportOption.ADVERTISEMENT,
    val reportContext: String = "",
    val reportButtonEnabled: Boolean = false
)
