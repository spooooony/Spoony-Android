package com.spoony.spoony.presentation.exploreSearch.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.spoony.spoony.core.navigation.Route
import com.spoony.spoony.presentation.exploreSearch.ExploreSearchRoute
import com.spoony.spoony.presentation.register.model.RegisterType
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
    navigateUp: () -> Unit,
    navigateToEditReview: (Int, RegisterType) -> Unit
) {
    composable<ExploreSearch>(
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        }
    ) {
        ExploreSearchRoute(
            paddingValues = paddingValues,
            navigateToUserProfile = navigateToUserProfile,
            navigateToReport = navigateToReport,
            navigateToPlaceDetail = navigateToPlaceDetail,
            navigateToEditReview = navigateToEditReview,
            navigateUp = navigateUp
        )
    }
}

@Serializable
data object ExploreSearch : Route
