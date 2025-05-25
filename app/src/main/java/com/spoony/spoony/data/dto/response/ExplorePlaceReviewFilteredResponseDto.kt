package com.spoony.spoony.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExplorePlaceReviewFilteredResponseDto(
    @SerialName("filteredFeedResponseDTOList")
    val filteredFeedResponseDTOList: List<PlaceReviewResponseDto>,
    @SerialName("nextCursor")
    val nextCursor: Int? = -1
)
