package com.spoony.spoony.presentation.placeDetail.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.spoony.spoony.core.navigation.Route
import com.spoony.spoony.presentation.map.navigaion.navigateToMap
import com.spoony.spoony.presentation.placeDetail.PlaceDetailRoute
import com.spoony.spoony.presentation.report.navigation.navigateToReport
import kotlinx.serialization.Serializable

fun NavController.navigateToPlaceDetail(
    navOptions: NavOptions? = null,
    postId: Int,
    userId: Int
) {
    navigate(PlaceDetail(postId, userId), navOptions)
}

fun NavGraphBuilder.placeDetailNavGraph(
    paddingValues: PaddingValues,
    navController: NavController
) {
    composable<PlaceDetail> {
        val args = it.toRoute<PlaceDetail>()
        PlaceDetailRoute(
            postId = args.postId,
            userId = args.userId,
            paddingValues = paddingValues,
            navigateToMap = {
                navController.navigateToMap()
            },
            navigateToReport = {
                navController.navigateToReport()
            }
        )
    }
}

@Serializable
data class PlaceDetail(val postId: Int, val userId: Int) : Route
