package com.spoony.spoony.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileInfoResponseDto(
    @SerialName("userName")
    val userName: String,
    @SerialName("regionName")
    val regionName: String?,
    @SerialName("introduction")
    val introduction: String?,
    @SerialName("birth")
    val birth: String?,
    @SerialName("imageLevel")
    val imageLevel: Int
)
