package com.spoony.spoony.presentation.userpage.otherpage

import androidx.activity.compose.BackHandler
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
import com.spoony.spoony.presentation.report.ReportType
import com.spoony.spoony.presentation.userpage.component.UserPageScreen
import com.spoony.spoony.presentation.userpage.model.UserPageEvents
import com.spoony.spoony.presentation.userpage.model.UserPageState
import com.spoony.spoony.presentation.userpage.model.UserProfile
import com.spoony.spoony.presentation.userpage.model.UserType

@Composable
fun OtherPageRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateToFollow: (FollowType, Int) -> Unit,
    navigateToReviewDetail: (Int) -> Unit,
    navigateToUserReport: (Int, ReportType) -> Unit,
    navigateToReviewReport: (Int, ReportType) -> Unit,
    navigateToEnterTab: () -> Unit,
    viewModel: OtherPageViewModel = hiltViewModel()
) {
    val userPageState by viewModel.state.collectAsStateWithLifecycle()
    val showSnackBar = LocalSnackBarTrigger.current
    val lifecycleOwner = LocalLifecycleOwner.current

    BackHandler {
        if (userPageState.isBlocked) {
            navigateToEnterTab()
        } else {
            navigateUp()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getUserProfile()
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle).collect { effect ->
            when (effect) {
                is OtherPageSideEffect.ShowSnackbar -> {
                    showSnackBar(effect.message)
                }
                is OtherPageSideEffect.ShowErrorSnackbar -> {
                    showSnackBar(effect.errorType.description)
                }
            }
        }
    }

    val userPageEvents = UserPageEvents(
        onBackButtonClick = {
            if (userPageState.isBlocked) {
                navigateToEnterTab()
            } else {
                navigateUp()
            }
        },
        onFollowClick = navigateToFollow,
        onReviewClick = navigateToReviewDetail,
        onReportUserClick = navigateToUserReport,
        onUserBlockClick = viewModel::blockUser,
        onMainButtonClick = viewModel::toggleFollow,
        onReportReviewClick = navigateToReviewReport,
        onCheckBoxClick = viewModel::toggleLocalReviewOnly
    )

    UserPageScreen(
        state = userPageState,
        events = userPageEvents,
        paddingValues = paddingValues
    )
}

@Preview(showBackground = true)
@Composable
private fun OtherScreenEmptyReviewPreview() {
    val paddingValues = PaddingValues(0.dp)
    SpoonyAndroidTheme {
        val previewState = UserPageState(
            userType = UserType.OTHER_PAGE,
            profile = UserProfile(
                profileId = 5,
                nickname = "게토 짱구루",
                region = "경기도 스푼",
                introduction = "문제아지만 세계최강",
                followerCount = 10,
                followingCount = 20,
                isFollowing = false
            ),
            isLocalReviewOnly = false
        )

        val previewEvents = UserPageEvents(
            onFollowClick = { _, _ -> },
            onReviewClick = { },
            onMainButtonClick = { },
            onBackButtonClick = { },
            onCheckBoxClick = { },
            onEditReviewClick = { _, _ -> }
        )

        UserPageScreen(
            state = previewState,
            events = previewEvents,
            paddingValues = paddingValues
        )
    }
}
