package com.spoony.spoony.presentation.report

sealed class ReportSideEffect {
    data object ShowDialog : ReportSideEffect()
}
