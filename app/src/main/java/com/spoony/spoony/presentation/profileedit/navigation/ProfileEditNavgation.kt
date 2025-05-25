package com.spoony.spoony.presentation.profileedit.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.spoony.spoony.core.navigation.Route
import com.spoony.spoony.presentation.profileedit.ProfileEditScreen
import kotlinx.serialization.Serializable

fun NavController.navigateToProfileEdit(
    navOptions: NavOptions? = null
) {
    navigate(ProfileEdit, navOptions)
}

fun NavGraphBuilder.profileEditGraph(
    navigateUp: () -> Unit
) {
    composable<ProfileEdit>(
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        }
    ) {
        ProfileEditScreen(
            onBackButtonClick = navigateUp
        )
    }
}

@Serializable
data object ProfileEdit : Route
