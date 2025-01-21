package com.spoony.spoony.presentation.explore.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.spoony.spoony.core.navigation.MainTabRoute
import com.spoony.spoony.presentation.explore.ExploreRoute
import com.spoony.spoony.presentation.placeDetail.navigation.navigateToPlaceDetail
import kotlinx.serialization.Serializable

fun NavController.navigateToExplore(
    navOptions: NavOptions? = null
) {
    navigate(Explore, navOptions)
}

fun NavGraphBuilder.exploreNavGraph(
    paddingValues: PaddingValues,
    navHostController: NavHostController
) {
    composable<Explore> {
        ExploreRoute(
            paddingValues = paddingValues,
            navigateToPlaceDetail = navHostController::navigateToPlaceDetail
        )
    }
}

@Serializable
data object Explore : MainTabRoute
