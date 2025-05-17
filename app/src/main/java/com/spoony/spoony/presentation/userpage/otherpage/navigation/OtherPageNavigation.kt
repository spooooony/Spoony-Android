package com.spoony.spoony.presentation.userpage.otherpage.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.spoony.spoony.core.navigation.Route
import com.spoony.spoony.presentation.follow.model.FollowType
import com.spoony.spoony.presentation.userpage.otherpage.OtherPageRoute
import kotlinx.serialization.Serializable

private const val LONG_ANIMATION_DURATION = 300

fun NavController.navigateToOtherPage(
    navOptions: NavOptions? = null,
    userId: Int
) {
    navigate(OtherPage(userId), navOptions)
}

fun NavGraphBuilder.otherPageNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateToFollow: (FollowType, Int) -> Unit,
    navigateToReviewDetail: (Int) -> Unit,
    navigateToUserReport: (Int) -> Unit,
    navigateToReviewReport: (Int, Int) -> Unit
) {
    composable<OtherPage>(
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(LONG_ANIMATION_DURATION)
            )
        }
    ) {
        OtherPageRoute(
            paddingValues = paddingValues,
            navigateUp = navigateUp,
            navigateToFollow = navigateToFollow,
            navigateToReviewDetail = navigateToReviewDetail,
            navigateToUserReport = navigateToUserReport,
            navigateToReviewReport = navigateToReviewReport
        )
    }
}

@Serializable
data class OtherPage(
    val userId: Int
) : Route
