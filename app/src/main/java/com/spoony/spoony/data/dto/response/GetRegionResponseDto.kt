package com.spoony.spoony.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetRegionResponseDto(
    @SerialName("regionId")
    val regionId: Int,
    @SerialName("regionName")
    val regionName: String
)
