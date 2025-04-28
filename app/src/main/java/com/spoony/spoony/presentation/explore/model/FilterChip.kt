package com.spoony.spoony.presentation.explore.model

import com.spoony.spoony.R

data class FilterChip(
    val sort: String,
    val text: String,
    val isSelected: Boolean = false,
    val leftIconResId: Int? = null,
    val rightIconResId: Int? = null,
    val onClick: () -> Unit = {}
)

object FilterChipDataProvider {

    fun getDefaultFilterChips() = listOf(
        FilterChip(
            sort = "filter",
            text = "필터",
            isSelected = false,
            leftIconResId = R.drawable.ic_filter_16
        ),
        FilterChip(
            sort = "local_review",
            text = "로컬 리뷰",
            isSelected = false
        ),
        FilterChip(
            sort = "category",
            text = "카테고리",
            isSelected = false,
            rightIconResId = R.drawable.ic_arrow_down_16
        ),
        FilterChip(
            sort = "region",
            text = "지역",
            isSelected = false,
            rightIconResId = R.drawable.ic_arrow_down_16
        ),
        FilterChip(
            sort = "age",
            text = "연령대",
            isSelected = false,
            rightIconResId = R.drawable.ic_arrow_down_16
        )
    )
}
