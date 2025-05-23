package com.spoony.spoony.presentation.gourmet.search.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.spoony.spoony.core.navigation.Route
import com.spoony.spoony.presentation.gourmet.search.MapSearchRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToMapSearch(
    navOptions: NavOptions? = null
) {
    navigate(MapSearch, navOptions)
}

fun NavGraphBuilder.mapSearchNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateToLocationMap: (Int, String, String, String, String) -> Unit
) {
    composable<MapSearch> {
        MapSearchRoute(
            paddingValues = paddingValues,
            navigateUp = navigateUp,
            navigateToLocationMap = navigateToLocationMap
        )
    }
}

@Serializable
data object MapSearch : Route
