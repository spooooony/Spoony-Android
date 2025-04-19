package com.spoony.spoony.presentation.splash.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.spoony.spoony.core.navigation.Route
import com.spoony.spoony.presentation.splash.SplashRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToSplash(
    navOptions: NavOptions? = null
) {
    navigate(
        Splash,
        navOptions
    )
}

fun NavGraphBuilder.splashNavGraph(
    paddingValues: PaddingValues,
    navigateToMap: () -> Unit,
    navigateToSignIn: () -> Unit
) {
    composable<Splash> {
        SplashRoute(
            navigateToMap = navigateToMap,
            navigateToSignIn = navigateToSignIn
        )
    }
}

@Serializable
data object Splash : Route
