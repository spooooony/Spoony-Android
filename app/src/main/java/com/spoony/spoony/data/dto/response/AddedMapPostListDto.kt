package com.spoony.spoony.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddedMapPostDto(
    @SerialName("placeId")
    val placeId: Int,
    @SerialName("placeName")
    val placeName: String,
    @SerialName("categoryColorResponse")
    val categoryColorResponse: CategoryColorResponse,
    @SerialName("authorName")
    val authorName: String,
    @SerialName("authorRegionName")
    val authorRegionName: String,
    @SerialName("postId")
    val postId: Int,
    @SerialName("postTitle")
    val postTitle: String,
    @SerialName("zzimCount")
    val zzimCount: Int,
    @SerialName("photoUrlList")
    val photoUrlList: List<String>
)

@Serializable
data class AddedMapPostListDto(
    @SerialName("zzimFocusResponseList")
    val zzimFocusResponseList: List<AddedMapPostDto>
)
