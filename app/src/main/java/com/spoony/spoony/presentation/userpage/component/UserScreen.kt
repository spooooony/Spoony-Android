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
import com.spoony.spoony.presentation.userpage.model.UserType
import com.spoony.spoony.presentation.userpage.mypage.component.MyPageTopAppBar

@Composable
fun UserPageScreen(
    userType: UserType,
    profileId: Int,
    userImageUrl: String,
    reviewCount: Int,
    followerCount: Int,
    followingCount: Int,
    region: String,
    userName: String,
    introduction: String,
    onFollowClick: (FollowType, Int) -> Unit,
    paddingValues: PaddingValues,
    onReviewClick: (Int) -> Unit,
    onMainButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    onEmptyClick: () -> Unit = {},
    spoonCount: Int = 0,
    isCheckBoxSelected: Boolean = true,
    onSettingClick: () -> Unit = {},
    onCheckBoxClick: () -> Unit = {},
    onLogoClick: () -> Unit = {},
    onBackButtonClick: () -> Unit = {},
    onMenuButtonClick: () -> Unit = {},
    isFollowing: Boolean = false
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(white)
            .padding(paddingValues)
    ) {
        item {
            when (userType) {
                UserType.MY_PAGE -> MyPageTopAppBar(
                    spoonCount = spoonCount,
                    onLogoClick = onLogoClick,
                    onIconClick = onSettingClick,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )

                UserType.OTHER_PAGE -> BackAndMenuTopAppBar(
                    onBackButtonClick = onBackButtonClick,
                    onMenuButtonClick = onMenuButtonClick,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }

            ProfileHeaderSection(
                imageUrl = userImageUrl,
                reviewCount = reviewCount,
                followerCount = followerCount,
                followingCount = followingCount,
                onFollowerClick = { onFollowClick(FollowType.FOLLOWER, profileId) },
                onFollowingClick = { onFollowClick(FollowType.FOLLOWING, profileId) },
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            ProfileIntroSection(
                userType = userType,
                region = region,
                nickname = userName,
                introduction = introduction,
                onButtonClick = onMainButtonClick,
                isFollowing = isFollowing,
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
                ReviewCounter(reviewCount = reviewCount)

                if (userType == UserType.OTHER_PAGE) {
                    LocalCheckBox(
                        isSelected = isCheckBoxSelected,
                        onClick = onCheckBoxClick
                    )
                }
            }
        }

        when (reviewCount) {
            0 -> {
                item {
                    Spacer(modifier = Modifier.height(54.dp))
                    when (userType) {
                        UserType.MY_PAGE -> {
                            MapEmptyBottomSheetContent(
                                onClick = onEmptyClick,
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
