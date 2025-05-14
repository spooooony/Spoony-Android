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
            text = "필터",
            isSelected = false,
            leftIconResId = R.drawable.ic_filter_16
        ),
        FilterOption(
            sort = FilterType.LOCAL_REVIEW,
            text = "로컬 리뷰",
            isSelected = false
        ),
        FilterOption(
            sort = FilterType.CATEGORY,
            text = "카테고리",
            isSelected = false,
            rightIconResId = R.drawable.ic_arrow_down_16
        ),
        FilterOption(
            sort = FilterType.REGION,
            text = "지역",
            isSelected = false,
            rightIconResId = R.drawable.ic_arrow_down_16
        ),
        FilterOption(
            sort = FilterType.AGE,
            text = "연령대",
            isSelected = false,
            rightIconResId = R.drawable.ic_arrow_down_16
        )
    )
}

enum class FilterType {
    FILTER,
    LOCAL_REVIEW,
    CATEGORY,
    REGION,
    AGE
}
