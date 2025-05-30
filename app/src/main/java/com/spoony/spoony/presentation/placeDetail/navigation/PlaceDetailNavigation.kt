package com.spoony.spoony.presentation.placeDetail.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.spoony.spoony.core.navigation.Route
import com.spoony.spoony.presentation.placeDetail.PlaceDetailRoute
import com.spoony.spoony.presentation.register.model.RegisterType
import com.spoony.spoony.presentation.report.ReportType
import kotlinx.serialization.Serializable

fun NavController.navigateToPlaceDetail(
    postId: Int,
    navOptions: NavOptions? = null
) {
    navigate(PlaceDetail(postId), navOptions)
}

fun NavGraphBuilder.placeDetailNavGraph(
    paddingValues: PaddingValues,
    navigateToReport: (reportTargetId: Int, type: ReportType) -> Unit,
    navigateToEditReview: (Int, RegisterType) -> Unit,
    navigateToUserProfile: (Int) -> Unit,
    navigateUp: () -> Unit
) {
    composable<PlaceDetail> {
        PlaceDetailRoute(
            paddingValues = paddingValues,
            navigateToReport = navigateToReport,
            navigateToEditReview = navigateToEditReview,
            navigateToUserProfile = navigateToUserProfile,
            navigateUp = navigateUp
        )
    }
}

@Serializable
data class PlaceDetail(val postId: Int) : Route
