package com.spoony.spoony.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 북마크 조회 API, 특정 지역 북마크 조회 API
 */

@Serializable
data class AddedMapResponseDto(
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
    val categoryColorResponse: CategoryColorResponse
)

@Serializable
data class AddedMapListResponseDto(
    @SerialName("count")
    val count: Int,
    @SerialName("zzimCardResponses")
    val zzimCardResponses: List<AddedMapResponseDto>
)
