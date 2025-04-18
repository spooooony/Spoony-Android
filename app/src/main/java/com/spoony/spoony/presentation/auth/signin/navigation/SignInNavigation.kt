package com.spoony.spoony.presentation.auth.signin.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.spoony.spoony.core.navigation.Route
import com.spoony.spoony.presentation.auth.signin.SignInRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToSignIn(
    navOptions: NavOptions? = null
) {
    navigate(
        SignIn,
        navOptions
    )
}

fun NavGraphBuilder.signInNavGraph(
    paddingValues: PaddingValues,
    navigateToMap: () -> Unit,
    navigateToTermsOfService: () -> Unit
) {
    composable<SignIn> {
        SignInRoute(
            paddingValues = paddingValues,
            navigateToMap = navigateToMap,
            navigateToTermsOfService = navigateToTermsOfService
        )
    }
}

@Serializable
data object SignIn : Route
