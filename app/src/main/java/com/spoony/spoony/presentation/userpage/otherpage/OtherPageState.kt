package com.spoony.spoony.presentation.userpage.otherpage

data class OtherPageState(
    val userProfile: UserProfile = UserProfile(),
    val isLocalReviewOnly: Boolean = false
)

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
