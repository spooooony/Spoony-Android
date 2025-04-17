package com.spoony.spoony.presentation.userpage.mypage

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.presentation.follow.model.FollowType
import com.spoony.spoony.presentation.userpage.component.UserPageScreen
import com.spoony.spoony.presentation.userpage.model.UserType

@Composable
fun MyPageRoute(
    paddingValues: PaddingValues,
    navigateToSettings: () -> Unit,
    navigateToFollow: (FollowType, Int) -> Unit,
    navigateToProfileEdit: () -> Unit,
    navigateToRegister: () -> Unit,
    navigateToReviewDetail: (Int) -> Unit,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getUserProfile()
        viewModel.getUserReviews()
        viewModel.getSpoonCount()
    }

    val spoonCount = state.spoonCount

    val userProfile = state.userProfile

    UserPageScreen(
        userType = UserType.MY_PAGE,
        profileId = userProfile.profileId,
        spoonCount = spoonCount,
        userImageUrl = userProfile.imageUrl,
        reviewCount = userProfile.reviewCount,
        followerCount = userProfile.followerCount,
        followingCount = userProfile.followingCount,
        region = userProfile.region,
        userName = userProfile.nickname,
        introduction = userProfile.introduction,
        onLogoClick = { /* 스푼 뽑기 */ },
        onSettingClick = navigateToSettings,
        onFollowClick = navigateToFollow,
        onProfileEditClick = navigateToProfileEdit,
        onEmptyClick = navigateToRegister,
        paddingValues = paddingValues,
        onReviewClick = navigateToReviewDetail
    )
}

@Preview(showBackground = true)
@Composable
private fun MyPageScreenEmptyReviewPreview() {
    val paddingValues = PaddingValues(0.dp)
    SpoonyAndroidTheme {
        UserPageScreen(
            userType = UserType.MY_PAGE,
            profileId = 4,
            spoonCount = 99,
            userImageUrl = "",
            reviewCount = 0,
            followerCount = 10,
            followingCount = 20,
            region = "서울 마포구 스푼",
            userName = "고졸 사토루",
            introduction = "두 사람은 문제아지만 최강.",
            onLogoClick = {},
            onSettingClick = {},
            onFollowClick = { followType, userId -> },
            onProfileEditClick = {},
            onEmptyClick = {},
            paddingValues = paddingValues,
            onReviewClick = {}
        )
    }
}
