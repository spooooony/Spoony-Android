package com.spoony.spoony.presentation.userpage.model

import com.spoony.spoony.presentation.follow.model.FollowType
import com.spoony.spoony.presentation.userpage.mypage.MyPageState
import com.spoony.spoony.presentation.userpage.otherpage.OtherPageState

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

data class UserPageState(
    val userType: UserType,
    val profile: UserProfile,
    // MyPage에만 필요한 속성
    val spoonCount: Int = 0,
    // OtherPage에만 필요한 속성
    val isLocalReviewOnly: Boolean = false
) {
    // UserPageScreen에 필요한 속성들을 쉽게 접근할 수 있는 확장 프로퍼티들
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
    val onMenuButtonClick: () -> Unit = {}
)

fun MyPageState.toUserPageState(userType: UserType = UserType.MY_PAGE): UserPageState {
    return UserPageState(
        userType = userType,
        profile = UserProfile(
            profileId = userProfile.profileId,
            imageUrl = userProfile.imageUrl,
            nickname = userProfile.nickname,
            region = userProfile.region,
            introduction = userProfile.introduction,
            reviewCount = userProfile.reviewCount,
            followerCount = userProfile.followerCount,
            followingCount = userProfile.followingCount
        ),
        spoonCount = spoonCount
    )
}

fun OtherPageState.toUserPageState(userType: UserType = UserType.OTHER_PAGE): UserPageState {
    return UserPageState(
        userType = userType,
        profile = UserProfile(
            profileId = userProfile.profileId,
            imageUrl = userProfile.imageUrl,
            nickname = userProfile.nickname,
            region = userProfile.region,
            introduction = userProfile.introduction,
            reviewCount = userProfile.reviewCount,
            followerCount = userProfile.followerCount,
            followingCount = userProfile.followingCount,
            isFollowing = userProfile.isFollowing
        ),
        isLocalReviewOnly = isLocalReviewOnly
    )
}
