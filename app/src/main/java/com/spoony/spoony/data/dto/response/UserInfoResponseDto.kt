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
    val plaformId: String,
    @SerialName("userName")
    val userName: String,
    @SerialName("userImageUrl")
    val userImageUrl: String,
    @SerialName("regionName")
    val regionName: String,
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("updatedAt")
    val updatedAt: String
)
