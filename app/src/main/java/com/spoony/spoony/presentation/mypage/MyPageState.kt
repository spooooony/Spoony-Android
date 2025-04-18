package com.spoony.spoony.presentation.mypage

data class MyPageState(
    val userProfile: UserProfile = UserProfile(),
    val spoonCount: Int = 0
)

data class UserProfile(
    val profileId: Int = 4,
    val imageUrl: String = "",
    val nickname: String = "",
    val region: String = "",
    val introduction: String = "",
    val reviewCount: Int = 0,
    val followerCount: Int = 0,
    val followingCount: Int = 0
)
