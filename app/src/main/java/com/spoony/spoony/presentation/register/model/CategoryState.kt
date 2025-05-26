package com.spoony.spoony.presentation.register.model

import androidx.compose.runtime.Stable
import com.spoony.spoony.domain.entity.CategoryEntity

@Stable
data class CategoryState(
    val categoryId: Int,
    val categoryName: String,
    val iconUrlNotSelected: String,
    val iconUrlSelected: String
)

fun CategoryEntity.toPresentation(): CategoryState =
    CategoryState(
        categoryId = categoryId,
        categoryName = categoryName,
        iconUrlSelected = iconUrl,
        iconUrlNotSelected = unSelectedIconUrl ?: ""
    )
