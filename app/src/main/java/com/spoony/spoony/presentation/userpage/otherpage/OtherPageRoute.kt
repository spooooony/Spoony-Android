package com.spoony.spoony.presentation.userpage.otherpage

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.presentation.follow.model.FollowType
import com.spoony.spoony.presentation.userpage.component.UserPageScreen
import com.spoony.spoony.presentation.userpage.model.UserPageEvents
import com.spoony.spoony.presentation.userpage.model.UserPageState
import com.spoony.spoony.presentation.userpage.model.UserType

@Composable
fun OtherPageRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateToFollow: (FollowType, Int) -> Unit,
    navigateToReviewDetail: (Int) -> Unit,
    viewModel: OtherPageViewModel = hiltViewModel()
) {
    val viewModelState by viewModel.state.collectAsStateWithLifecycle()
    val userPageState = remember(viewModelState) {
        viewModel.createUserPageState()
    }

    val userPageEvents = UserPageEvents(
        onFollowClick = navigateToFollow,
        onReviewClick = navigateToReviewDetail,
        onMainButtonClick = viewModel::toggleFollow,
        onBackButtonClick = navigateUp,
        onMenuButtonClick = { /* 드롭다운 */ },
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
            profileId = 5,
            userImageUrl = "",
            reviewCount = 0,
            followerCount = 10,
            followingCount = 20,
            region = "경기도 스푼",
            userName = "게토 짱구루",
            introduction = "문제아지만 세계최강",
            isFollowing = false,
            isCheckBoxSelected = false
        )

        val previewEvents = UserPageEvents(
            onFollowClick = { _, _ -> },
            onReviewClick = { },
            onMainButtonClick = { },
            onBackButtonClick = { },
            onMenuButtonClick = { },
            onCheckBoxClick = { }
        )

        UserPageScreen(
            state = previewState,
            events = previewEvents,
            paddingValues = paddingValues
        )
    }
}
