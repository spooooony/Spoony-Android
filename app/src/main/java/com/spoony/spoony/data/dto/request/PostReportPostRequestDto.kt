package com.spoony.spoony.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostReportPostRequestDto(
    @SerialName("postId")
    val postId: Int,
    @SerialName("userId")
    val userId: Int,
    @SerialName("reportType")
    val reportType: String,
    @SerialName("reportDetail")
    val reportDetail: String
)
