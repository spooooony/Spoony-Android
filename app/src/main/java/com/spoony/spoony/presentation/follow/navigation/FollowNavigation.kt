package com.spoony.spoony.presentation.follow.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.spoony.spoony.core.navigation.Route
import com.spoony.spoony.presentation.follow.FollowRoute
import com.spoony.spoony.presentation.follow.model.FollowType
import kotlinx.serialization.Serializable

private const val LONG_ANIMATION_DURATION = 300

fun NavController.navigateToFollow(
    followType: FollowType,
    userId: Int,
    navOptions: NavOptions = navOptions {
        launchSingleTop = true
    }
) {
    navigate(Follow(followType, userId), navOptions)
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
        }
    ) {
        FollowRoute(
            paddingValues = paddingValues,
            navigateToUserProfile = navigateToUserProfile,
            onBackButtonClick = navigateUp
        )
    }
}

@Serializable
data class Follow(
    val followType: FollowType,
    val userId: Int
) : Route
