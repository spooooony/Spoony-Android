package com.spoony.spoony.presentation.map.navigaion

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.spoony.spoony.core.navigation.MainTabRoute
import com.spoony.spoony.presentation.map.MapScreen
import kotlinx.serialization.Serializable

fun NavController.navigateToMap(
    navOptions: NavOptions? = null
) {
    navigate(Map, navOptions)
}

fun NavGraphBuilder.mapNavGraph() {
    composable<Map> {
        MapScreen()
    }
}

@Serializable
data object Map : MainTabRoute
