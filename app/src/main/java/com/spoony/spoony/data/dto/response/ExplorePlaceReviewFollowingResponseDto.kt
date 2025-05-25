package com.spoony.spoony.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExplorePlaceReviewFollowingResponseDto(
    @SerialName("feedResponseList")
    val feedsResponseList: List<PlaceReviewResponseDto>
)
