package com.spoony.spoony.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetPostResponseDto(
    @SerialName("postId")
    val postId: Int,
    @SerialName("userId")
    val userId: Int,
    @SerialName("photoUrlList")
    val photoUrlList: List<String>,
    @SerialName("date")
    val date: String,
    @SerialName("menuList")
    val menuList: List<String>,
    @SerialName("description")
    val description: String,
    @SerialName("value")
    val value: Double,
    @SerialName("cons")
    val cons: String?,
    @SerialName("placeName")
    val placeName: String,
    @SerialName("placeAddress")
    val placeAddress: String,
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("zzimCount")
    val zzimCount: Int,
    @SerialName("isMine")
    val isMine: Boolean,
    @SerialName("isZzim")
    val isZzim: Boolean,
    @SerialName("isScoop")
    val isScoop: Boolean,
    @SerialName("categoryColorResponse")
    val categoryColorResponse: CategoryColorResponse
)

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
