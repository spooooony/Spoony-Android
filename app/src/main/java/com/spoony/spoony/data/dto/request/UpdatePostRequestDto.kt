package com.spoony.spoony.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdatePostRequestDto(
    @SerialName("postId")
    val postId: Int,
    @SerialName("description")
    val description: String,
    @SerialName("value")
    val value: Float,
    @SerialName("cons")
    val cons: String?,
    @SerialName("categoryId")
    val categoryId: Int,
    @SerialName("menuList")
    val menuList: List<String>,
    @SerialName("deleteImageUrlList")
    val deleteImageUrlList: List<String>
)
