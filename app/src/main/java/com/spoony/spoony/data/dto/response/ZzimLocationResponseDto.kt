package com.spoony.spoony.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 특정 지역의 북마크 조회 API
 */

@Serializable
data class ZzimLocationResponseDto(
    @SerialName("count")
    val count: Int,
    @SerialName("zzimCardResponses")
    val zzimCardResponses: List<ZzimCardResponseDto>
)

@Serializable
data class ZzimCardResponseDto(
    @SerialName("placeId")
    val placeId: Int,
    @SerialName("placeName")
    val placeName: String,
    @SerialName("placeAddress")
    val placeAddress: String,
    @SerialName("description")
    val description: String,
    @SerialName("photoUrl")
    val photoUrl: String,
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("categoryColorResponse")
    val categoryColorResponse: CategoryColorResponseDto
)

@Serializable
data class CategoryColorResponseDto(
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
