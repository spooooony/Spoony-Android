package com.spoony.spoony.presentation.userpage.model

import com.spoony.spoony.core.designsystem.model.ReviewCardCategory
import com.spoony.spoony.presentation.follow.model.FollowType
import com.spoony.spoony.presentation.register.model.RegisterType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class UserProfile(
    val profileId: Int = 0,
    val imageUrl: String = "",
    val nickname: String = "",
    val region: String = "",
    val introduction: String = "",
    val reviewCount: Int = 0,
    val followerCount: Int = 0,
    val followingCount: Int = 0,
    val isFollowing: Boolean = false
)

data class ReviewData(
    val reviewId: Int = 0,
    val content: String = "",
    val category: ReviewCardCategory? = null,
    val username: String = "",
    val userRegion: String = "",
    val date: String = "",
    val addMapCount: Int = 0,
    val imageList: ImmutableList<String> = persistentListOf()
)

data class UserPageState(
    val userType: UserType,
    val profile: UserProfile,
    val spoonCount: Int = 0,
    val isLocalReviewOnly: Boolean = true,
    val reviews: ImmutableList<ReviewData> = persistentListOf()
) {
    val profileId get() = profile.profileId
    val userImageUrl get() = profile.imageUrl
    val reviewCount get() = profile.reviewCount
    val followerCount get() = profile.followerCount
    val followingCount get() = profile.followingCount
    val region get() = profile.region
    val userName get() = profile.nickname
    val introduction get() = profile.introduction
    val isFollowing get() = profile.isFollowing
    val isCheckBoxSelected get() = isLocalReviewOnly
}

data class UserPageEvents(
    val onFollowClick: (FollowType, Int) -> Unit,
    val onReviewClick: (Int) -> Unit,
    val onMainButtonClick: () -> Unit,
    val onEmptyClick: () -> Unit = {},
    val onSettingClick: () -> Unit = {},
    val onCheckBoxClick: () -> Unit = {},
    val onLogoClick: () -> Unit = {},
    val onBackButtonClick: () -> Unit = {},
    val onMenuButtonClick: () -> Unit = {},
    val onDeleteReviewClick: (Int) -> Unit = {},
    val onReportReviewClick: (Int, Int) -> Unit = {_, _ ->},
    val onEditReviewClick: (Int, RegisterType) -> Unit = { _, _ -> }
)
