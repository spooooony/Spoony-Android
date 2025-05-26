package com.spoony.spoony.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserPageReviewResponseDto(
    @SerialName("feedResponseList")
    val feedResponseList: List<FeedResponse>
)

@Serializable
data class FeedResponse(
    @SerialName("userId")
    val userId: Int,
    @SerialName("userName")
    val userName: String,
    @SerialName("userRegion")
    val userRegion: String,
    @SerialName("postId")
    val postId: Int,
    @SerialName("description")
    val description: String,
    @SerialName("categoryColorResponse")
    val categoryColorResponse: CategoryColorResponse,
    @SerialName("zzimCount")
    val zzimCount: Int,
    @SerialName("photoUrlList")
    val photoUrlList: List<String>,
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("isMine")
    val isMine: Boolean
)
