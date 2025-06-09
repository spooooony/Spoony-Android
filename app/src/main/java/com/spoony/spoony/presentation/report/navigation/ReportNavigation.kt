package com.spoony.spoony.presentation.report.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.spoony.spoony.core.navigation.Route
import com.spoony.spoony.presentation.report.ReportRoute
import com.spoony.spoony.presentation.report.ReportType
import kotlinx.serialization.Serializable

fun NavController.navigateToReport(
    reportTargetId: Int,
    type: ReportType,
    navOptions: NavOptions? = null
) {
    navigate(Report(reportTargetId, type), navOptions)
}

fun NavGraphBuilder.reportNavGraph(
    navigateUp: () -> Unit,
    navigateToEnterTab: () -> Unit,
    paddingValues: PaddingValues
) {
    composable<Report> {
        ReportRoute(
            navigateUp = navigateUp,
            navigateToEnterTab = navigateToEnterTab,
            paddingValues = paddingValues
        )
    }
}

@Serializable
data class Report(val reportTargetId: Int, val type: ReportType) : Route
