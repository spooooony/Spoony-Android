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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.card.ReviewCard
import com.spoony.spoony.core.designsystem.component.dialog.TwoButtonDialog
import com.spoony.spoony.core.designsystem.component.topappbar.BackAndMenuTopAppBar
import com.spoony.spoony.core.designsystem.model.ReviewCardCategory
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.theme.gray0
import com.spoony.spoony.core.designsystem.theme.main400
import com.spoony.spoony.core.designsystem.theme.white
import com.spoony.spoony.core.designsystem.type.ButtonStyle
import com.spoony.spoony.presentation.follow.model.FollowType
import com.spoony.spoony.presentation.gourmet.map.component.bottomsheet.MapEmptyBottomSheetContent
import com.spoony.spoony.presentation.register.model.RegisterType
import com.spoony.spoony.presentation.report.ReportType
import com.spoony.spoony.presentation.userpage.model.UserPageEvents
import com.spoony.spoony.presentation.userpage.model.UserPageState
import com.spoony.spoony.presentation.userpage.model.UserType
import com.spoony.spoony.presentation.userpage.mypage.component.MyPageTopAppBar
import kotlinx.collections.immutable.persistentListOf

@Composable
fun UserPageScreen(
    state: UserPageState,
    events: UserPageEvents,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    var isReviewDeleteDialogVisible by remember { mutableStateOf(false) }
    var isUserBlockDialogVisible by remember { mutableStateOf(false) }
    val topBarMenuItemList = persistentListOf("차단하기", "신고하기")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(white)
            .then(
                if (state.userType == UserType.OTHER_PAGE) {
                    modifier.padding(bottom = paddingValues.calculateBottomPadding())
                } else {
                    modifier.padding(paddingValues)
                }
            )

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
                    isMenuIconVisible = !state.isBlocked,
                    menuItemList = topBarMenuItemList,
                    onBackButtonClick = events.onBackButtonClick,
                    onMenuButtonClick = { menuItem ->
                        when (menuItem) {
                            "차단하기" -> {
                                isUserBlockDialogVisible = true
                            }

                            "신고하기" -> events.onReportUserClick(state.profileId, ReportType.USER)
                        }
                    },
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }

            ProfileHeaderSection(
                imageUrl = state.userImageUrl,
                reviewCount = if (state.isBlocked) 0 else state.reviewCount,
                followerCount = if (state.isBlocked) 0 else state.followerCount,
                followingCount = if (state.isBlocked) 0 else state.followingCount,
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
                onButtonClick = {
                    if (state.isBlocked) {
                        isUserBlockDialogVisible = true
                    } else {
                        events.onMainButtonClick()
                    }
                },
                isFollowing = state.isFollowing,
                isBlocked = state.isBlocked,
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ReviewCounter(reviewCount = state.reviews.size)

                if (state.userType == UserType.OTHER_PAGE) {
                    LocalReviewFilterCheckBox(
                        enabled = !state.isBlocked,
                        isSelected = state.isCheckBoxSelected,
                        onClick = events.onCheckBoxClick
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (isUserBlockDialogVisible) {
                TwoButtonDialog(
                    message = if (state.isBlocked) "${state.userName} 님을\n차단 해제하시겠습니까?" else "${state.userName} 님을\n차단하시겠습니까?",
                    negativeText = "아니요",
                    positiveText = "네",
                    onClickNegative = { isUserBlockDialogVisible = false },
                    onClickPositive = {
                        events.onUserBlockClick(state.profileId)
                        isUserBlockDialogVisible = false
                    },
                    onDismiss = { }
                )
            }
        }

        if (state.isBlocked) {
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier
                        .padding(vertical = 24.dp)
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(R.drawable.img_empty_home),
                        modifier = Modifier
                            .size(100.dp)
                            .padding(bottom = 16.dp),
                        contentDescription = null
                    )
                    Text(
                        text = "차단된 사용자예요.",
                        style = SpoonyAndroidTheme.typography.body2b,
                        color = SpoonyAndroidTheme.colors.gray500,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "지금은 프로필을 볼 수 없지만, \n" +
                                "원하시면 차단을 해제할 수 있어요.",
                        style = SpoonyAndroidTheme.typography.body2m,
                        color = SpoonyAndroidTheme.colors.gray500,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            when (state.reviews.size) {
                0 -> item {
                    Spacer(modifier = Modifier.height(54.dp))
                    when (state.userType) {
                        UserType.MY_PAGE -> MapEmptyBottomSheetContent(
                            onClick = events.onEmptyClick,
                            description = "아직 등록한 리뷰가 없어요\n나만의 찐맛집을 공유해 보세요!",
                            buttonText = "등록하러 가기",
                            buttonStyle = ButtonStyle.Primary
                        )

                        UserType.OTHER_PAGE -> EmptyContent(
                            text = when (state.isLocalReviewOnly) {
                                true -> "아직 등록된 로컬리뷰가 없어요"
                                false -> "아직 등록된 리뷰가 없어요"
                            }
                        )
                    }
                }

                else -> items(
                    items = state.reviews,
                    key = { review -> review.reviewId }
                ) { review ->
                    val menuItems = when (state.userType) {
                        UserType.MY_PAGE -> persistentListOf("수정하기", "삭제하기")
                        UserType.OTHER_PAGE -> persistentListOf("신고하기")
                    }

                    ReviewCard(
                        reviewId = review.reviewId,
                        review = review.content,
                        category = review.category ?: ReviewCardCategory(
                            text = "맛집",
                            iconUrl = "",
                            textColor = main400,
                            backgroundColor = SpoonyAndroidTheme.colors.main100
                        ),
                        username = review.username,
                        userRegion = review.userRegion,
                        date = review.date,
                        onMenuItemClick = { menuItem ->
                            when (menuItem) {
                                "수정하기" -> events.onEditReviewClick(review.reviewId, RegisterType.EDIT)

                                "삭제하기" -> {
                                    isReviewDeleteDialogVisible = true
                                }

                                "신고하기" -> events.onReportReviewClick(review.reviewId, ReportType.POST)
                            }
                        },
                        menuItems = menuItems,
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                        addMapCount = review.addMapCount,
                        onClick = events.onReviewClick,
                        imageList = review.imageList
                    )

                    if (isReviewDeleteDialogVisible) {
                        TwoButtonDialog(
                            message = "정말로 리뷰를 삭제할까요?",
                            negativeText = "아니요",
                            positiveText = "네",
                            onClickNegative = { isReviewDeleteDialogVisible = false },
                            onClickPositive = {
                                events.onDeleteReviewClick(review.reviewId)
                                isReviewDeleteDialogVisible = false
                            },
                            onDismiss = { }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyContent(
    text: String,
    modifier: Modifier = Modifier
) {
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
            text = text,
            style = SpoonyAndroidTheme.typography.body2m,
            color = SpoonyAndroidTheme.colors.gray500,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(vertical = 16.dp)
        )
    }
}
