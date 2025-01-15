package com.spoony.spoony.presentation.explore.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.spoony.spoony.core.navigation.MainTabRoute
import com.spoony.spoony.presentation.explore.ExploreScreen
import kotlinx.serialization.Serializable

fun NavController.navigateExplore(
    navOptions: NavOptions? = null
) {
    navigate(Explore, navOptions)
}

fun NavGraphBuilder.exploreNavGraph(
    paddingValues: PaddingValues
) {
    composable<Explore> {
        ExploreScreen()
    }
}

@Serializable
data object Explore : MainTabRoute
