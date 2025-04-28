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
import com.spoony.spoony.presentation.userpage.model.UserPageEvents
import com.spoony.spoony.presentation.userpage.model.UserPageState
import com.spoony.spoony.presentation.userpage.model.UserProfile
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
    val userPageState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getUserProfile()
        viewModel.getSpoonCount()
    }

    val userPageEvents = UserPageEvents(
        onFollowClick = navigateToFollow,
        onReviewClick = navigateToReviewDetail,
        onMainButtonClick = navigateToProfileEdit,
        onEmptyClick = navigateToRegister,
        onSettingClick = navigateToSettings,
        onLogoClick = { /* 스푼 뽑기 */ }
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
                nickname = "고졸 사토루",
                region = "서울 마포구 스푼",
                introduction = "두 사람은 문제아지만 최강.",
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
