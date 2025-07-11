package com.spoony.spoony.presentation.userpage.mypage

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.spoony.spoony.core.designsystem.event.LocalSnackBarTrigger
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.presentation.follow.model.FollowType
import com.spoony.spoony.presentation.register.model.RegisterType
import com.spoony.spoony.presentation.userpage.component.UserPageScreen
import com.spoony.spoony.presentation.userpage.model.UserPageEvents
import com.spoony.spoony.presentation.userpage.model.UserPageState
import com.spoony.spoony.presentation.userpage.model.UserProfile
import com.spoony.spoony.presentation.userpage.model.UserType

@Composable
fun MyPageRoute(
    paddingValues: PaddingValues,
    navigateToSettings: () -> Unit,
    navigateToProfileEdit: () -> Unit,
    navigateToRegister: () -> Unit,
    navigateToFollow: (FollowType, Int) -> Unit,
    navigateToEditReview: (Int, RegisterType) -> Unit,
    navigateToReviewDetail: (Int) -> Unit,
    navigateToAttendance: () -> Unit,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val userPageState by viewModel.state.collectAsStateWithLifecycle()
    val showSnackBar = LocalSnackBarTrigger.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        viewModel.getUserProfile()
        viewModel.getSpoonCount()
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle).collect { effect ->
            when (effect) {
                is MyPageSideEffect.ShowSnackbar -> {
                    showSnackBar(effect.message)
                }
                is MyPageSideEffect.ShowError -> {
                    showSnackBar(effect.errorType.description)
                }
            }
        }
    }

    val userPageEvents = UserPageEvents(
        onSettingClick = navigateToSettings,
        onMainButtonClick = navigateToProfileEdit,
        onEmptyClick = navigateToRegister,
        onFollowClick = navigateToFollow,
        onReviewClick = navigateToReviewDetail,
        onEditReviewClick = navigateToEditReview,
        onDeleteReviewClick = viewModel::deleteReview,
        onLogoClick = navigateToAttendance
    )

    UserPageScreen(
        state = userPageState,
        events = userPageEvents,
        paddingValues = paddingValues
    )
}

@Preview(showBackground = true)
@Composable
private fun MyPageScreenEmptyReviewPreview() {
    val paddingValues = PaddingValues(0.dp)
    SpoonyAndroidTheme {
        val previewState = UserPageState(
            userType = UserType.MY_PAGE,
            profile = UserProfile(
                profileId = 4,
                nickname = "톳시",
                region = "에도 막부",
                introduction = "오타쿠의 패왕",
                followerCount = 10,
                followingCount = 20
            ),
            spoonCount = 99
        )

        val previewEvents = UserPageEvents(
            onFollowClick = { _, _ -> },
            onReviewClick = { },
            onMainButtonClick = { },
            onEmptyClick = { },
            onSettingClick = { },
            onLogoClick = { }
        )

        UserPageScreen(
            state = previewState,
            events = previewEvents,
            paddingValues = paddingValues
        )
    }
}
