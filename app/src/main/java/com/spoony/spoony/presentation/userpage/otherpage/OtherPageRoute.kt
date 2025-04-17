package com.spoony.spoony.presentation.userpage.otherpage

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
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
    navigateToSettings: () -> Unit,
    navigateToFollow: (FollowType, Int) -> Unit,
    navigateToProfileEdit: () -> Unit,
    navigateToRegister: () -> Unit,
    navigateToReviewDetail: (Int) -> Unit,
    viewModel: OtherPageViewModel = hiltViewModel()
) {
    UserPageScreen(
        userType = UserType.OTHER_PAGE,
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
private fun OtherScreenEmptyReviewPreview() {
    val paddingValues = PaddingValues(0.dp)
    SpoonyAndroidTheme {
        UserPageScreen(
            userType = UserType.MY_PAGE,
            profileId = 5,
            spoonCount = 99,
            userImageUrl = "",
            reviewCount = 0,
            followerCount = 10,
            followingCount = 20,
            region = "경기도 스푼",
            userName = "Queen 효빈",
            introduction = "Makers 36th 효빈입니다.",
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
