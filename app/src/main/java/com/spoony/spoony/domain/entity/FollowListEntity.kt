package com.spoony.spoony.domain.entity

data class FollowListEntity(
    val count: Int,
    val users: List<User>
)

data class User(
    val userId: Int,
    val username: String,
    val regionName: String?,
    val isFollowing: Boolean,
    val profileImageUrl: String,
    val isMe: Boolean
)
