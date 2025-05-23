package com.spoony.spoony.presentation.setting.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.spoony.spoony.core.navigation.Route
import com.spoony.spoony.presentation.setting.SettingRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToSettingPage(
    navOptions: NavOptions? = null
) {
    navigate(SettingPage, navOptions)
}

fun NavGraphBuilder.settingPageNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit
) {
    composable<SettingPage> {
        SettingRoute(
            paddingValues = paddingValues,
            navigateUp = navigateUp
        )
    }
}

@Serializable
data object SettingPage : Route
