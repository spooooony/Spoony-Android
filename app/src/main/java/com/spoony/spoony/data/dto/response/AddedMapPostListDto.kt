package com.spoony.spoony.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 특정 장소의 북마크 리스트 조회
 */
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
    val authorRegionName: String?,
    @SerialName("postId")
    val postId: Int,
    @SerialName("description")
    val description: String,
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
