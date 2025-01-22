package com.spoony.spoony.presentation.map.navigaion

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.spoony.spoony.core.navigation.MainTabRoute
import com.spoony.spoony.presentation.map.MapRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToMap(
    navOptions: NavOptions? = null
) {
    navigate(Map, navOptions)
}

fun NavGraphBuilder.mapNavGraph(
    paddingValues: PaddingValues,
    navigateToPlaceDetail: (Int) -> Unit,
    navigateToMapSearch: () -> Unit,
    navigateUp: () -> Unit
) {
    composable<Map> {
        MapRoute(
            paddingValues = paddingValues,
            navigateToPlaceDetail = navigateToPlaceDetail,
            navigateToMapSearch = navigateToMapSearch,
            navigateUp = navigateUp
        )
    }
}

@Serializable
data object Map : MainTabRoute
