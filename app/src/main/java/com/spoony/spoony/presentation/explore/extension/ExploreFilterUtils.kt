package com.spoony.spoony.presentation.explore.extension

import com.spoony.spoony.presentation.explore.model.FilterType

fun handleFilterClick(
    filterType: FilterType,
    onLocalReviewButtonClick: () -> Unit,
    updateBottomSheetState: (Int, Boolean) -> Unit
) {
    when (filterType) {
        FilterType.FILTER -> updateBottomSheetState(0, true)
        FilterType.LOCAL_REVIEW -> onLocalReviewButtonClick()
        FilterType.CATEGORY -> updateBottomSheetState(1, true)
        FilterType.REGION -> updateBottomSheetState(2, true)
        FilterType.AGE -> updateBottomSheetState(3, true)
    }
}
