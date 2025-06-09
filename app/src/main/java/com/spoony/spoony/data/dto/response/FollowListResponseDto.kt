package com.spoony.spoony.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FollowListResponseDto(
    @SerialName("count")
    val count: Int,
    @SerialName("users")
    val users: List<User>
)

@Serializable
data class User(
    @SerialName("userId")
    val userId: Int,
    @SerialName("username")
    val username: String,
    @SerialName("regionName")
    val regionName: String?,
    @SerialName("isFollowing")
    val isFollowing: Boolean,
    @SerialName("profileImageUrl")
    val profileImageUrl: String,
    @SerialName("isMine")
    val isMe: Boolean
)

// TODO: 파일 분리하기
@Serializable
data class BlockedUser(
    @SerialName("userId")
    val userId: Int,
    @SerialName("username")
    val username: String,
    @SerialName("regionName")
    val regionName: String?,
    @SerialName("isBlocked")
    val isBlocked: Boolean,
    @SerialName("profileImageUrl")
    val profileImageUrl: String,
    @SerialName("isMine")
    val isMe: Boolean
)
