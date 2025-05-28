package com.spoony.spoony.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileUpdateRequestDto(
    @SerialName("userName")
    val userName: String,
    @SerialName("regionId")
    val regionId: Int?,
    @SerialName("introduction")
    val introduction: String?,
    @SerialName("birth")
    val birth: String?,
    @SerialName("imageLevel")
    val imageLevel: Int
)
