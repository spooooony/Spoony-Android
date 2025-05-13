package com.spoony.spoony.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReportUserRequestDto(
    @SerialName("targetUserId")
    val targetUserId: Int,
    @SerialName("userReportType")
    val userReportType: String,
    @SerialName("reportDetail")
    val reportDetail: String
)
