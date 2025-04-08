package com.spoony.spoony.presentation.follow.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.spoony.spoony.core.navigation.Route
import com.spoony.spoony.presentation.follow.FollowMainScreen
import com.spoony.spoony.presentation.follow.FollowViewModel
import com.spoony.spoony.presentation.follow.FollowerScreen
import com.spoony.spoony.presentation.follow.FollowingScreen
import com.spoony.spoony.presentation.main.NAVIGATION_ROOT
import com.spoony.spoony.presentation.register.navigation.Register
import kotlinx.serialization.Serializable

fun NavController.navigateToFollow(
    navOptions: NavOptions? = null
) {
    navigate(Follow, navOptions)
}

fun NavGraphBuilder.followNavGraph(
    paddingValues: PaddingValues,
    navigateToUserProfile: (Int) -> Unit
) {
    composable<Follow> {
        FollowMainScreen(
            paddingValues = paddingValues,
            navigateToUserProfile = navigateToUserProfile
        )
    }
}

fun NavHostController.navigateToFollowTab(route: FollowRoute) {
    currentDestination?.route?.let { currentRoute ->
        val navOptions = navOptions {
            popUpTo(currentRoute) {
                inclusive = true
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
        navigate(route, navOptions)
    } ?: navigate(route)
}

fun NavGraphBuilder.followGraph(
    navigateToUserProfile: (Int) -> Unit,
    viewModel: FollowViewModel
) {
    composable<FollowRoute.Follower>(
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        }
    ) {
        FollowerScreen(
            navigateToUserProfile = navigateToUserProfile,
            viewModel = viewModel
        )
    }

    composable<FollowRoute.Following>(
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
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        }
    ) {
        FollowingScreen(
            navigateToUserProfile = navigateToUserProfile,
            viewModel = viewModel
        )
    }
}

@Serializable
data object Follow : Route
