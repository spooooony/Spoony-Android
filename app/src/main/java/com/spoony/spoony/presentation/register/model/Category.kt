package com.spoony.spoony.presentation.register.model

import androidx.compose.runtime.Stable

@Stable
data class Category(
    val categoryId: Int,
    val categoryName: String,
    val iconUrlNotSelected: String,
    val iconUrlSelected: String
)
