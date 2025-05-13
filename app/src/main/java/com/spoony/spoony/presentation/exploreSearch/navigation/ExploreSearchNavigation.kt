package com.spoony.spoony.presentation.exploreSearch.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.spoony.spoony.core.navigation.Route
import com.spoony.spoony.presentation.exploreSearch.ExploreSearchRoute
import com.spoony.spoony.presentation.report.ReportType
import kotlinx.serialization.Serializable

fun NavController.navigateToExploreSearch(
    navOptions: NavOptions? = null
) {
    navigate(ExploreSearch, navOptions)
}

fun NavGraphBuilder.exploreSearchNavGraph(
    paddingValues: PaddingValues,
    navigateToUserProfile: (Int) -> Unit,
    navigateToReport: (reportTargetId: Int, type: ReportType) -> Unit,
    navigateToPlaceDetail: (Int) -> Unit,
    navigateUp: () -> Unit
) {
    composable<ExploreSearch> {
        ExploreSearchRoute(
            paddingValues = paddingValues,
            navigateToUserProfile = navigateToUserProfile,
            navigateToReport = navigateToReport,
            navigateToPlaceDetail = navigateToPlaceDetail,
            navigateUp = navigateUp
        )
    }
}

@Serializable
data object ExploreSearch : Route
