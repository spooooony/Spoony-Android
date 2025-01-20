package com.spoony.spoony.domain.entity

data class CategoryEntity(
    val categoryId: Int,
    val categoryName: String,
    val iconUrl: String,
    val unSelectedIconUrl: String? = null,
    val textColor: String? = null,
    val backgroundColor: String? = null
)
