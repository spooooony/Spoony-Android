package com.spoony.spoony.presentation.placeDetail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.spoony.spoony.core.navigation.MainTabRoute
import com.spoony.spoony.presentation.placeDetail.PlaceDetailRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToPlaceDetail(
    navOptions: NavOptions? = null
) {
    navigate(PlaceDetail, navOptions)
}

fun NavGraphBuilder.placeDetailNavGraph() {
    composable<PlaceDetail> {
        PlaceDetailRoute()
    }
}

@Serializable
data object PlaceDetail : MainTabRoute
