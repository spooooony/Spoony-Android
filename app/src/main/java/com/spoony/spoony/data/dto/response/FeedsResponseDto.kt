package com.spoony.spoony.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FeedsResponseDto(
    @SerialName("feedResponseList")
    val feedsResponseList: List<FeedsResponse>
) {
    @Serializable
    data class FeedsResponse(
        @SerialName("userId")
        val userId: Int,
        @SerialName("userName")
        val userName: String,
        @SerialName("userRegion")
        val userRegion: String,
        @SerialName("postId")
        val postId: Int,
        @SerialName("title")
        val title: String,
        @SerialName("categoryColorResponse")
        val categoryColorResponse: CategoryColorResponse,
        @SerialName("zzimCount")
        val zzimCount: Int,
        @SerialName("createdAt")
        val createdAt: String
    ) {
        @Serializable
        data class CategoryColorResponse(
            @SerialName("categoryId")
            val categoryId: Int,
            @SerialName("categoryName")
            val categoryName: String,
            @SerialName("iconUrl")
            val iconUrl: String,
            @SerialName("iconTextColor")
            val iconTextColor: String,
            @SerialName("iconBackgroundColor")
            val iconBackgroundColor: String
        )
    }
}
