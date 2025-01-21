package com.spoony.spoony.presentation.map.search.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.spoony.spoony.core.navigation.Route
import com.spoony.spoony.presentation.map.search.MapSearchRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToMapSearch(
    navOptions: NavOptions? = null
) {
    navigate(MapSearch, navOptions)
}

fun NavGraphBuilder.mapSearchNavGraph(
    paddingValues: PaddingValues
) {
    composable<MapSearch> {
        MapSearchRoute()
    }
}

@Serializable
data object MapSearch : Route
