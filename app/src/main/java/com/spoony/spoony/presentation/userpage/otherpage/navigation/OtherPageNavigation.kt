package com.spoony.spoony.presentation.userpage.otherpage.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.spoony.spoony.core.navigation.Route
import com.spoony.spoony.presentation.follow.model.FollowType
import com.spoony.spoony.presentation.userpage.otherpage.OtherPageRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToOhterPage(
    navOptions: NavOptions? = null,
    userId: Int
) {
    navigate(OtherPage(userId), navOptions)
}

fun NavGraphBuilder.OtherPageNavGraph(
    paddingValues: PaddingValues,
    navigateToSettings: () -> Unit,
    navigateToFollow: (FollowType, Int) -> Unit,
    navigateToProfileEdit: () -> Unit,
    navigateToRegister: () -> Unit,
    navigateToReviewDetail: (Int) -> Unit
) {
    composable<OtherPage> {
        OtherPageRoute(
            paddingValues = paddingValues,
            navigateToSettings = navigateToSettings,
            navigateToFollow = navigateToFollow,
            navigateToProfileEdit = navigateToProfileEdit,
            navigateToRegister = navigateToRegister,
            navigateToReviewDetail = navigateToReviewDetail
        )
    }
}

@Serializable
data class OtherPage(
    val userId: Int
) : Route
