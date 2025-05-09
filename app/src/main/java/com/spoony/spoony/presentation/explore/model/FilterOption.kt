package com.spoony.spoony.presentation.explore.model

import com.spoony.spoony.R
import kotlinx.collections.immutable.persistentListOf

data class FilterOption(
    val sort: String,
    val text: String,
    val isSelected: Boolean = false,
    val leftIconResId: Int? = null,
    val rightIconResId: Int? = null,
    val onClick: () -> Unit = {}
)

object FilterChipOptionProvider {

    fun getDefaultFilterOptions() = persistentListOf(
        FilterOption(
            sort = "filter",
            text = "필터",
            isSelected = false,
            leftIconResId = R.drawable.ic_filter_16
        ),
        FilterOption(
            sort = "local_review",
            text = "로컬 리뷰",
            isSelected = false
        ),
        FilterOption(
            sort = "category",
            text = "카테고리",
            isSelected = false,
            rightIconResId = R.drawable.ic_arrow_down_16
        ),
        FilterOption(
            sort = "region",
            text = "지역",
            isSelected = false,
            rightIconResId = R.drawable.ic_arrow_down_16
        ),
        FilterOption(
            sort = "age",
            text = "연령대",
            isSelected = false,
            rightIconResId = R.drawable.ic_arrow_down_16
        )
    )
}
