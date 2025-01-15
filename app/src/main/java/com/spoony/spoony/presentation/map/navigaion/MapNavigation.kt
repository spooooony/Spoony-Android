package com.spoony.spoony.presentation.map.navigaion

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.spoony.spoony.core.navigation.MainTabRoute
import kotlinx.serialization.Serializable

fun NavController.navigateMap(
    navOptions: NavOptions? = null
) {
    navigate(Map, navOptions)
}

fun NavGraphBuilder.MapNavGraph(
    paddingValues: PaddingValues
) {
    composable<Map> {
    }
}


@Serializable
data object Map : MainTabRoute
