package com.spoony.spoony.presentation.userpage.otherpage

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.presentation.follow.model.FollowType
import com.spoony.spoony.presentation.userpage.component.UserPageScreen
import com.spoony.spoony.presentation.userpage.model.UserType

@Composable
fun OtherPageRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateToFollow: (FollowType, Int) -> Unit,
    navigateToReviewDetail: (Int) -> Unit,
    viewModel: OtherPageViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val userProfile = state.userProfile

    UserPageScreen(
        paddingValues = paddingValues,
        userType = UserType.OTHER_PAGE,
        profileId = userProfile?.profileId ?: 5,
        userImageUrl = userProfile?.imageUrl ?: "",
        reviewCount = userProfile?.reviewCount ?: 0,
        followerCount = userProfile?.followerCount ?: 0,
        followingCount = userProfile?.followingCount ?: 0,
        region = userProfile?.region ?: "",
        userName = userProfile?.nickname ?: "",
        introduction = userProfile?.introduction ?: "",
        onBackButtonClick = navigateUp,
        onMenuButtonClick = { /* */ },
        onFollowClick = navigateToFollow,
        onReviewClick = navigateToReviewDetail,
        onMainButtonClick = viewModel::toggleFollow,
        isFollowing = userProfile?.isFollowing ?: false,
        isCheckBoxSelected = state.isLocalReviewOnly,
        onCheckBoxClick = viewModel::toggleLocalReviewOnly
    )
}

@Preview(showBackground = true)
@Composable
private fun OtherScreenEmptyReviewPreview() {
    val paddingValues = PaddingValues(0.dp)
    SpoonyAndroidTheme {
        UserPageScreen(
            userType = UserType.OTHER_PAGE,
            profileId = 5,
            userImageUrl = "",
            reviewCount = 0,
            followerCount = 10,
            followingCount = 20,
            region = "경기도 스푼",
            userName = "게토 짱구루",
            introduction = "문제아지만 세계최강",
            onFollowClick = { followType, userId -> },
            onMainButtonClick = {},
            paddingValues = paddingValues,
            onReviewClick = {}
        )
    }
}
