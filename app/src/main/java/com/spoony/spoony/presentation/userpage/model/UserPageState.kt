package com.spoony.spoony.presentation.userpage.model

import com.spoony.spoony.presentation.follow.model.FollowType

data class UserPageState(
    val userType: UserType,
    val profileId: Int,
    val userImageUrl: String,
    val reviewCount: Int,
    val followerCount: Int,
    val followingCount: Int,
    val region: String,
    val userName: String,
    val introduction: String,
    val spoonCount: Int = 0,
    val isCheckBoxSelected: Boolean = true,
    val isFollowing: Boolean = false
)

data class UserPageEvents(
    val onFollowClick: (FollowType, Int) -> Unit,
    val onReviewClick: (Int) -> Unit,
    val onMainButtonClick: () -> Unit,
    val onEmptyClick: () -> Unit = {},
    val onSettingClick: () -> Unit = {},
    val onCheckBoxClick: () -> Unit = {},
    val onLogoClick: () -> Unit = {},
    val onBackButtonClick: () -> Unit = {},
    val onMenuButtonClick: () -> Unit = {}
)
