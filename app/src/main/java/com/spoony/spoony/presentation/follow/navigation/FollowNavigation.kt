package com.spoony.spoony.presentation.follow.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
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
import kotlinx.serialization.Serializable

fun NavController.navigateToFollow(
    isFollowing: Boolean = true,
    navOptions: NavOptions? = null
) {
    navigate(Follow(isFollowing), navOptions)
}

fun NavGraphBuilder.followNavGraph(
    paddingValues: PaddingValues,
    navigateToUserProfile: (Int) -> Unit,
    navigateUp: () -> Unit
) {
    composable<Follow>(
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(600)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(600)
            )
        }
    ) {
        FollowMainScreen(
            paddingValues = paddingValues,
            navigateToUserProfile = navigateToUserProfile,
            onBackButtonClick = navigateUp,
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
        }
    ) {
        FollowingScreen(
            navigateToUserProfile = navigateToUserProfile,
            viewModel = viewModel
        )
    }
}

@Serializable
data class Follow(val isFollowing: Boolean) : Route
