package com.spoony.spoony.presentation.explore.model

import com.spoony.spoony.R
import kotlinx.collections.immutable.persistentListOf

data class FilterOption(
    val sort: FilterType,
    val text: String,
    val isSelected: Boolean = false,
    val leftIconResId: Int? = null,
    val rightIconResId: Int? = null
)

object FilterChipOptionProvider {

    fun getDefaultFilterOptions() = persistentListOf(
        FilterOption(
            sort = FilterType.FILTER,
            text = FilterType.FILTER.defaultText,
            isSelected = false,
            leftIconResId = R.drawable.ic_filter_16
        ),
        FilterOption(
            sort = FilterType.LOCAL_REVIEW,
            text = FilterType.LOCAL_REVIEW.defaultText,
            isSelected = false
        ),
        FilterOption(
            sort = FilterType.CATEGORY,
            text = FilterType.CATEGORY.defaultText,
            isSelected = false,
            rightIconResId = R.drawable.ic_arrow_down_16
        ),
        FilterOption(
            sort = FilterType.REGION,
            text = FilterType.CATEGORY.defaultText,
            isSelected = false,
            rightIconResId = R.drawable.ic_arrow_down_16
        ),
        FilterOption(
            sort = FilterType.AGE,
            text = FilterType.AGE.defaultText,
            isSelected = false,
            rightIconResId = R.drawable.ic_arrow_down_16
        )
    )
}

enum class FilterType(val defaultText: String) {
    FILTER(defaultText = "필터"),
    LOCAL_REVIEW(defaultText = "로컬 리뷰"),
    CATEGORY(defaultText = "카테고리"),
    REGION(defaultText = "지역"),
    AGE(defaultText = "연령대")
}
