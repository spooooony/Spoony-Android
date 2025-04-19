package com.spoony.spoony.presentation.auth.termsofservice.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.spoony.spoony.core.navigation.Route
import com.spoony.spoony.presentation.auth.termsofservice.TermsOfServiceRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToTermsOfService(
    navOptions: NavOptions? = null
) {
    navigate(
        TermsOfService,
        navOptions
    )
}

fun NavGraphBuilder.termsOfServiceNavGraph(
    paddingValues: PaddingValues,
    navigateToMap: () -> Unit
) {
    composable<TermsOfService> {
        TermsOfServiceRoute(
            paddingValues = paddingValues,
            navigateToMap = navigateToMap
        )
    }
}

@Serializable
data object TermsOfService : Route
