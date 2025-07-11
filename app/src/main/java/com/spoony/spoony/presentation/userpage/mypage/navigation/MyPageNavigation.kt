package com.spoony.spoony.presentation.userpage.mypage.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.spoony.spoony.core.navigation.MainTabRoute
import com.spoony.spoony.presentation.follow.model.FollowType
import com.spoony.spoony.presentation.register.model.RegisterType
import com.spoony.spoony.presentation.userpage.mypage.MyPageRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToMyPage(
    navOptions: NavOptions? = null
) {
    navigate(MyPage, navOptions)
}

fun NavGraphBuilder.myPageNavGraph(
    paddingValues: PaddingValues,
    navigateToSettings: () -> Unit,
    navigateToFollow: (FollowType, Int) -> Unit,
    navigateToProfileEdit: () -> Unit,
    navigateToRegister: () -> Unit,
    navigateToReviewDetail: (Int) -> Unit,
    navigateToEditReview: (Int, RegisterType) -> Unit,
    navigateToAttendance: () -> Unit
) {
    composable<MyPage> {
        MyPageRoute(
            paddingValues = paddingValues,
            navigateToSettings = navigateToSettings,
            navigateToFollow = navigateToFollow,
            navigateToProfileEdit = navigateToProfileEdit,
            navigateToRegister = navigateToRegister,
            navigateToReviewDetail = navigateToReviewDetail,
            navigateToEditReview = navigateToEditReview,
            navigateToAttendance = navigateToAttendance
        )
    }
}

@Serializable
data object MyPage : MainTabRoute
