package com.spoony.spoony.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponseDto(
    @SerialName("userId")
    val userId: Int,
    @SerialName("platform")
    val platform: String,
    @SerialName("platformId")
    val platformId: String,
    @SerialName("userName")
    val userName: String,
    @SerialName("regionName")
    val regionName: String,
    @SerialName("introduction")
    val introduction: String,
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("updatedAt")
    val updatedAt: String,
    @SerialName("followerCount")
    val followerCount: Int,
    @SerialName("followingCount")
    val followingCount: Int,
    @SerialName("isFollowing")
    val isFollowing: Boolean,
    @SerialName("reviewCount")
    val reviewCount: Int,
    @SerialName("profileImageUrl")
    val profileImageUrl: String
)
