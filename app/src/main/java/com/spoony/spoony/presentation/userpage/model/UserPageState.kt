package com.spoony.spoony.presentation.userpage.model

import androidx.compose.ui.graphics.Color
import com.spoony.spoony.core.designsystem.model.ReviewCardCategory
import com.spoony.spoony.core.util.extension.hexToColor
import com.spoony.spoony.core.util.extension.toValidHexColor
import com.spoony.spoony.domain.entity.BasicUserInfoEntity
import com.spoony.spoony.domain.entity.UserFeedEntity
import com.spoony.spoony.domain.entity.UserPageReviewEntity
import com.spoony.spoony.presentation.follow.model.FollowType
import com.spoony.spoony.presentation.register.model.RegisterType
import com.spoony.spoony.presentation.report.ReportType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

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

fun BasicUserInfoEntity.toModel(): UserProfile = UserProfile(
    profileId = this.userId,
    imageUrl = this.profileImageUrl,
    nickname = this.userName,
    region = this.regionName ?: "",
    introduction = this.introduction,
    reviewCount = this.reviewCount,
    followerCount = this.followerCount,
    followingCount = this.followingCount,
    isFollowing = this.isFollowing
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

fun UserPageReviewEntity.toModel(): ImmutableList<ReviewData> =
    this.feedList.map { it.toModel() }.toImmutableList()

fun UserFeedEntity.toModel(): ReviewData = ReviewData(
    reviewId = this.postId,
    content = this.description,
    category = ReviewCardCategory(
        text = this.categoryInfo.categoryName,
        iconUrl = this.categoryInfo.iconUrl,
        textColor = Color.hexToColor(this.categoryInfo.textColor.toValidHexColor()),
        backgroundColor = Color.hexToColor(this.categoryInfo.backgroundColor.toValidHexColor())
    ),
    username = this.userName,
    userRegion = this.userRegion,
    date = this.createdAt,
    addMapCount = this.zzimCount,
    imageList = this.photoUrlList.toImmutableList()
)

data class UserPageState(
    val userType: UserType,
    val profile: UserProfile,
    val spoonCount: Int = 0,
    val isLocalReviewOnly: Boolean = false,
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
    val onReportUserClick: (Int, ReportType) -> Unit = { _, _ -> },
    val onUserBlockClick: (Int) -> Unit = {}
)
