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

private const val LONG_ANIMATION_DURATION = 300

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
                animationSpec = tween(LONG_ANIMATION_DURATION)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(LONG_ANIMATION_DURATION)
            )
        }
    ) {
        FollowMainScreen(
            paddingValues = paddingValues,
            navigateToUserProfile = navigateToUserProfile,
            onBackButtonClick = navigateUp
        )
    }
}

@Serializable
data class Follow(val isFollowing: Boolean) : Route
