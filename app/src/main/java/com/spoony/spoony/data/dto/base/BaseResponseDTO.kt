package com.spoony.spoony.data.dto.base

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponseDTO<T>(
    @SerialName("success")
    val success: Boolean,
    @SerialName("error")
    val error: ErrorResponse? = null,
    @SerialName("data")
    val data: T? = null
)

@Serializable
data class ErrorResponse(
    @SerialName("message")
    val message: String
)
