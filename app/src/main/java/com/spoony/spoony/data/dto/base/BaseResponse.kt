package com.spoony.spoony.data.dto.base

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    @SerialName("success")
    val success: Boolean,
    @SerialName("error")
    val error: String,
    @SerialName("data")
    val data: T? = null
)
