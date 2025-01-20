package com.spoony.spoony.presentation.placeDetail.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.spoony.spoony.core.navigation.Route
import com.spoony.spoony.presentation.placeDetail.PlaceDetailRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToPlaceDetail(
    navOptions: NavOptions? = null
) {
    navigate(PlaceDetail, navOptions)
}

fun NavGraphBuilder.placeDetailNavGraph(
    paddingValues: PaddingValues
) {
    composable<PlaceDetail> {
        PlaceDetailRoute(
            paddingValues = paddingValues
        )
    }
}

@Serializable
data object PlaceDetail : Route
