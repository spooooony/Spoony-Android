package com.spoony.spoony.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegionListResponseDto(
    @SerialName("regionList")
    val regionList: List<RegionResponseDto>
)

@Serializable
data class RegionResponseDto(
    @SerialName("regionId")
    val regionId: Int,
    @SerialName("regionName")
    val regionName: String
)
