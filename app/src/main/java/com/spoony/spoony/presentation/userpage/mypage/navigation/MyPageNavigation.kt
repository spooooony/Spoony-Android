package com.spoony.spoony.presentation.userpage.mypage.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.spoony.spoony.core.navigation.MainTabRoute
import com.spoony.spoony.presentation.follow.model.FollowType
import com.spoony.spoony.presentation.follow.navigation.shouldApplyFollowPageExitAnimation
import com.spoony.spoony.presentation.userpage.mypage.MyPageRoute
import com.spoony.spoony.presentation.userpage.otherpage.navigation.LONG_ANIMATION_DURATION
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
    navigateToReviewDetail: (Int) -> Unit
) {
    composable(route = MyPage.toString()) {
        MyPageRoute(
            paddingValues = paddingValues,
            navigateToSettings = navigateToSettings,
            navigateToFollow = { followType, userId ->
                // 마이페이지에서 팔로우 페이지로 이동할 때 Exit 애니메이션 활성화
                shouldApplyFollowPageExitAnimation = true
                navigateToFollow(followType, userId)
            },
            navigateToProfileEdit = navigateToProfileEdit,
            navigateToRegister = navigateToRegister,
            navigateToReviewDetail = navigateToReviewDetail
        )
    }
}

@Serializable
data object MyPage : MainTabRoute
