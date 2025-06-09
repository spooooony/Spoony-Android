package com.spoony.spoony.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserListSearchResponseDto(
    @SerialName("userSimpleResponseDTO")
    val userSimpleResponseDTO: List<UserSimpleResponseDto>
)

@Serializable
data class UserSimpleResponseDto(
    @SerialName("userId")
    val userId: Int,
    @SerialName("username")
    val username: String,
    @SerialName("regionName")
    val regionName: String?,
    @SerialName("profileImageUrl")
    val profileImageUrl: String,
    @SerialName("isMine")
    val isMine: Boolean
)
