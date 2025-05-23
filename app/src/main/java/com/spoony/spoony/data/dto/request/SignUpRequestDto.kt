package com.spoony.spoony.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequestDto(
    @SerialName("platform")
    val platform: String,
    @SerialName("userName")
    val userName: String,
    @SerialName("birth")
    val birth: String?,
    @SerialName("regionId")
    val regionId: Int?,
    @SerialName("introduction")
    val introduction: String?
)
