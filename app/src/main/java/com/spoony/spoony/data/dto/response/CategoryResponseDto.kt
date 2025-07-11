package com.spoony.spoony.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponseDto(
    @SerialName("categoryMonoList")
    val categoryMonoList: List<CategoryMonoDto>
)

@Serializable
data class FoodCategoryResponseDto(
    @SerialName("categoryMonoList")
    val categoryMonoList: List<CategoryMonoDto>
)

@Serializable
data class CategoryMonoDto(
    @SerialName("categoryId")
    val categoryId: Int,
    @SerialName("categoryName")
    val categoryName: String,
    @SerialName("iconUrlNotSelected")
    val iconUrlNotSelected: String,
    @SerialName("iconUrlSelected")
    val iconUrlSelected: String
)

@Serializable
data class CategoryColorDto(
    @SerialName("categoryId")
    val categoryId: Int,
    @SerialName("categoryName")
    val categoryName: String,
    @SerialName("iconUrl")
    val iconUrl: String,
    @SerialName("iconTextColor")
    val iconTextColor: String,
    @SerialName("iconBackgroundColor")
    val iconBackgroundColor: String
)
