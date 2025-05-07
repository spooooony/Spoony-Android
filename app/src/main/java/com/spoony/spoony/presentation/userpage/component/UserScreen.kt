package com.spoony.spoony.presentation.userpage.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.topappbar.BackAndMenuTopAppBar
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.theme.gray0
import com.spoony.spoony.core.designsystem.theme.white
import com.spoony.spoony.core.designsystem.type.ButtonStyle
import com.spoony.spoony.presentation.follow.model.FollowType
import com.spoony.spoony.presentation.gourmet.map.component.bottomsheet.MapEmptyBottomSheetContent
import com.spoony.spoony.presentation.register.model.RegisterType
import com.spoony.spoony.presentation.userpage.model.UserPageEvents
import com.spoony.spoony.presentation.userpage.model.UserPageState
import com.spoony.spoony.presentation.userpage.model.UserType
import com.spoony.spoony.presentation.userpage.mypage.component.MyPageTopAppBar

@Composable
fun UserPageScreen(
    state: UserPageState,
    events: UserPageEvents,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    navigateToEditReview: (Int, RegisterType) -> Unit = { _, _ -> }
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(white)
            .padding(paddingValues)
    ) {
        item {
            when (state.userType) {
                UserType.MY_PAGE -> MyPageTopAppBar(
                    spoonCount = state.spoonCount,
                    onLogoClick = events.onLogoClick,
                    onIconClick = events.onSettingClick,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )

                UserType.OTHER_PAGE -> BackAndMenuTopAppBar(
                    onBackButtonClick = events.onBackButtonClick,
                    onMenuButtonClick = events.onMenuButtonClick,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }

            ProfileHeaderSection(
                imageUrl = state.userImageUrl,
                reviewCount = state.reviewCount,
                followerCount = state.followerCount,
                followingCount = state.followingCount,
                onFollowerClick = { events.onFollowClick(FollowType.FOLLOWER, state.profileId) },
                onFollowingClick = { events.onFollowClick(FollowType.FOLLOWING, state.profileId) },
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            ProfileIntroSection(
                userType = state.userType,
                region = state.region,
                nickname = state.userName,
                introduction = state.introduction,
                onButtonClick = events.onMainButtonClick,
                isFollowing = state.isFollowing,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(27.dp))

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 4.dp,
                color = gray0
            )

            Spacer(modifier = Modifier.height(19.dp))

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ReviewCounter(reviewCount = state.reviewCount)

                if (state.userType == UserType.OTHER_PAGE) {
                    LocalReviewFilterCheckBox(
                        isSelected = state.isCheckBoxSelected,
                        onClick = events.onCheckBoxClick
                    )
                }
            }

            Button(
                modifier = Modifier,
                onClick = { navigateToEditReview(0, RegisterType.EDIT) }
            ) {
                Text(text = "Edit Review")
            }
        }

        when (state.reviewCount) {
            0 -> {
                item {
                    Spacer(modifier = Modifier.height(54.dp))
                    when (state.userType) {
                        UserType.MY_PAGE -> {
                            MapEmptyBottomSheetContent(
                                onClick = events.onEmptyClick,
                                description = "아직 등록한 리뷰가 없어요\n나만의 찐맛집을 공유해 보세요!",
                                buttonText = "등록하러 가기",
                                buttonStyle = ButtonStyle.Primary
                            )
                        }
                        UserType.OTHER_PAGE -> {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = modifier
                                    .padding(vertical = 24.dp)
                                    .fillMaxWidth()
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.img_empty_home),
                                    modifier = Modifier.size(100.dp),
                                    contentDescription = null
                                )
                                Text(
                                    text = "아직 등록된 로컬리뷰가 없어요.",
                                    style = SpoonyAndroidTheme.typography.body2m,
                                    color = SpoonyAndroidTheme.colors.gray500,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .padding(vertical = 16.dp)
                                )
                            }
                        }
                    }
                }
            }

            else -> {
                // TODO: 실제 리뷰 데이터로 교체 필요
            }
        }
    }
}
