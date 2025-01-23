package com.spoony.spoony.presentation.report.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.spoony.spoony.core.navigation.Route
import com.spoony.spoony.presentation.report.ReportRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToReport(
    postId: Int,
    navOptions: NavOptions? = null
) {
    navigate(Report(postId), navOptions)
}

fun NavGraphBuilder.reportNavGraph(
    navigateUp: () -> Unit,
    navigateToExplore: () -> Unit,
    paddingValues: PaddingValues
) {
    composable<Report> {
        ReportRoute(
            navigateUp = navigateUp,
            navigateToExplore = navigateToExplore,
            paddingValues = paddingValues
        )
    }
}

@Serializable
data class Report(val postId: Int) : Route
