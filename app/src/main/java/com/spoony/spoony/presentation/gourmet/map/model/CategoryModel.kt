package com.spoony.spoony.presentation.gourmet.map.model

import com.spoony.spoony.domain.entity.CategoryEntity

data class CategoryModel(
    val categoryId: Int = 0,
    val categoryName: String = "",
    val iconUrl: String = "",
    val unSelectedIconUrl: String = "",
    val textColor: String = "",
    val backgroundColor: String = ""
)

fun CategoryEntity.toModel(): CategoryModel = CategoryModel(
    categoryId = this.categoryId,
    categoryName = this.categoryName,
    iconUrl = this.iconUrl,
    unSelectedIconUrl = this.unSelectedIconUrl ?: "",
    textColor = this.textColor ?: "",
    backgroundColor = this.backgroundColor ?: ""
)
