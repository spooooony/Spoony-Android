package com.spoony.spoony.presentation.userpage.model

import com.spoony.spoony.core.designsystem.model.ReviewCardCategory
import com.spoony.spoony.presentation.follow.model.FollowType
import com.spoony.spoony.presentation.register.model.RegisterType
import com.spoony.spoony.presentation.report.ReportType
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
    val isFollowing: Boolean = false,
    val isBlocked: Boolean = false
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
    val isBlocked get() = profile.isBlocked
}

data class UserPageEvents(
    // 공통
    val onFollowClick: (FollowType, Int) -> Unit,
    val onMainButtonClick: () -> Unit,
    val onReviewClick: (Int) -> Unit,

    // 마이페이지
    val onDeleteReviewClick: (Int) -> Unit = {},
    val onEditReviewClick: (Int, RegisterType) -> Unit = { _, _ -> },
    val onEmptyClick: () -> Unit = {},
    val onLogoClick: () -> Unit = {},
    val onSettingClick: () -> Unit = {},

    // 유저페이지
    val onBackButtonClick: () -> Unit = {},
    val onCheckBoxClick: () -> Unit = {},
    val onReportReviewClick: (Int, ReportType) -> Unit = { _, _ -> },
    val onReportUserClick: (Int) -> Unit = {},
    val onUserBlockClick: (Int) -> Unit = {}
)
