package com.spoony.spoony.presentation.placeDetail.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.spoony.spoony.core.navigation.Route
import com.spoony.spoony.presentation.placeDetail.PlaceDetailRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToPlaceDetail(
    postId: Int,
    userId: Int,
    navOptions: NavOptions? = null
) {
    navigate(PlaceDetail(postId, userId), navOptions)
}

fun NavGraphBuilder.placeDetailNavGraph(
    paddingValues: PaddingValues,
    navigateToReport: () -> Unit
) {
    composable<PlaceDetail> {
        val args = it.toRoute<PlaceDetail>()
        PlaceDetailRoute(
            postId = args.postId,
            userId = args.userId,
            paddingValues = paddingValues,
            navigateToReport = navigateToReport
        )
    }
}

@Serializable
data class PlaceDetail(val postId: Int, val userId: Int) : Route
