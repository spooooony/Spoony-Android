package com.spoony.spoony.presentation.myPage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.theme.gray0
import com.spoony.spoony.core.designsystem.theme.white
import com.spoony.spoony.core.designsystem.type.ButtonStyle
import com.spoony.spoony.presentation.gourmet.map.component.bottomsheet.MapEmptyBottomSheetContent
import com.spoony.spoony.presentation.myPage.component.MyPageTopAppBar
import com.spoony.spoony.presentation.myPage.component.ProfileHeaderSection
import com.spoony.spoony.presentation.myPage.component.ProfileIntroSection
import com.spoony.spoony.presentation.myPage.component.ReviewCounter

@Composable
fun MyPageRoute(
    paddingValues: PaddingValues,
    navigateToSettings: () -> Unit,
    navigateToFollower: () -> Unit,
    navigateToFollowing: () -> Unit,
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

    MyPageScreen(
        spoonCount = spoonCount,
        userImageUrl = userProfile?.imageUrl ?: "",
        reviewCount = userProfile?.reviewCount ?: 0,
        followerCount = userProfile?.followerCount ?: 0,
        followingCount = userProfile?.followingCount ?: 0,
        region = userProfile?.region ?: "",
        userName = userProfile?.nickname ?: "",
        introduction = userProfile?.introduction ?: "",
        onLogoClick = { /* 스푼 뽑기 */ },
        onSettingClick = navigateToSettings,
        onFollowerClick = navigateToFollower,
        onFollowingClick = navigateToFollowing,
        onProfileEditClick = navigateToProfileEdit,
        onEmptyClick = navigateToRegister,
        paddingValues = paddingValues,
        onReviewClick = navigateToReviewDetail
    )
}

@Composable
fun MyPageScreen(
    spoonCount: Int,
    userImageUrl: String,
    reviewCount: Int,
    followerCount: Int,
    followingCount: Int,
    region: String,
    userName: String,
    introduction: String,
    onLogoClick: () -> Unit,
    onSettingClick: () -> Unit,
    onFollowerClick: () -> Unit,
    onFollowingClick: () -> Unit,
    onProfileEditClick: () -> Unit,
    onEmptyClick: () -> Unit,
    paddingValues: PaddingValues,
    onReviewClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(white)
            .padding(paddingValues)
    ) {
        item {
            MyPageTopAppBar(
                spoonCount = spoonCount,
                onLogoClick = onLogoClick,
                onIconClick = onSettingClick,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            ProfileHeaderSection(
                imageUrl = userImageUrl,
                reviewCount = reviewCount,
                followerCount = followerCount,
                followingCount = followingCount,
                onFollowerClick = onFollowerClick,
                onFollowingClick = onFollowingClick,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            ProfileIntroSection(
                region = region,
                nickname = userName,
                introduction = introduction,
                buttonText = "프로필 수정",
                onButtonClick = onProfileEditClick,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(27.dp))

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 4.dp,
                color = gray0
            )

            Spacer(modifier = Modifier.height(19.dp))

            ReviewCounter(
                reviewCount = reviewCount,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
        }

        when (reviewCount) {
            0 -> {
                item {
                    Spacer(modifier = Modifier.height(42.dp))

                    MapEmptyBottomSheetContent(
                        onClick = onEmptyClick,
                        description = "아직 등록한 리뷰가 없어요\n나만의 찐맛집을 공유해 보세요!",
                        buttonText = "등록하러 가기",
                        buttonStyle = ButtonStyle.Primary,
                        modifier = Modifier
                    )
                }
            }

            else -> {
                // TODO: 실제 리뷰 데이터로 교체 필요
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MyPageScreenEmptyReviewPreview() {
    val paddingValues = PaddingValues(0.dp)
    SpoonyAndroidTheme {
        MyPageScreen(
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
            onFollowerClick = {},
            onFollowingClick = {},
            onProfileEditClick = {},
            onEmptyClick = {},
            paddingValues = paddingValues,
            onReviewClick = {}
        )
    }
}
