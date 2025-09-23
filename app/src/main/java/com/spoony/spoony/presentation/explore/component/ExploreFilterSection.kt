package com.spoony.spoony.presentation.explore.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.spoony.spoony.presentation.explore.ExploreAction
import com.spoony.spoony.presentation.explore.ExploreFilterItems
import com.spoony.spoony.presentation.explore.ExploreFilterState
import com.spoony.spoony.presentation.explore.ExploreType
import com.spoony.spoony.presentation.explore.MutableExploreFilterState
import com.spoony.spoony.presentation.explore.component.bottomsheet.ExploreFilterBottomSheet
import com.spoony.spoony.presentation.explore.component.bottomsheet.ExploreSortingBottomSheet
import com.spoony.spoony.presentation.explore.extension.handleFilterClick
import com.spoony.spoony.presentation.explore.extension.toggle
import com.spoony.spoony.presentation.explore.model.FilterCategory
import com.spoony.spoony.presentation.explore.model.FilterOption
import com.spoony.spoony.presentation.explore.toMutable
import com.spoony.spoony.presentation.explore.type.SortingOption
import kotlinx.collections.immutable.ImmutableList

@Composable
fun ExploreFilterSection(
    exploreType: ExploreType,
    chipItems: ImmutableList<FilterOption>,
    selectedSortingOption: SortingOption,
    selectedFilterState: ExploreFilterState,
    filterItems: ExploreFilterItems,
    onAction: (ExploreAction) -> Unit
) {
    var isSortingBottomSheetVisible by remember { mutableStateOf(false) }
    var isFilterBottomSheetVisible by remember { mutableStateOf(false) }
    var exploreFilterBottomSheetTabIndex by remember { mutableIntStateOf(0) }

    val tempFilterState = remember(isFilterBottomSheetVisible) {
        if (isFilterBottomSheetVisible) {
            selectedFilterState.toMutable()
        } else {
            MutableExploreFilterState()
        }
    }

    if (exploreType == ExploreType.ALL) {
        FilterChipRow(
            chipItems,
            onFilterClick = { filterType ->
                handleFilterClick(
                    filterType = filterType,
                    onLocalReviewButtonClick = { onAction(ExploreAction.ClickLocalReview) },
                    updateBottomSheetState = { index, isVisible ->
                        exploreFilterBottomSheetTabIndex = index
                        isFilterBottomSheetVisible = isVisible
                    }
                )
            },
            onSortFilterClick = { isSortingBottomSheetVisible = true }
        )
    }

    if (isSortingBottomSheetVisible) {
        ExploreSortingBottomSheet(
            onDismiss = { isSortingBottomSheetVisible = false },
            onClick = { onAction(ExploreAction.ChangeSorting(it)) },
            currentSortingOption = selectedSortingOption
        )
    }

    if (isFilterBottomSheetVisible) {
        ExploreFilterBottomSheet(
            onDismiss = { isFilterBottomSheetVisible = false },
            onFilterReset = { tempFilterState.reset() },
            onSave = {
                isFilterBottomSheetVisible = false
                onAction(ExploreAction.ApplyFilter(tempFilterState.toPersistent()))
            },
            onToggleFilter = { id, type ->
                FilterCategory.fromFilterType(type)?.let { filterCategory ->
                    when (filterCategory) {
                        FilterCategory.LOCAL_REVIEW -> tempFilterState.properties.toggle(id)
                        FilterCategory.CATEGORY -> tempFilterState.categories.toggle(id)
                        FilterCategory.REGION -> tempFilterState.regions.toggle(id)
                        FilterCategory.AGE -> tempFilterState.ages.toggle(id)
                    }
                }
            },
            filterItems = filterItems,
            filterState = tempFilterState,
            tabIndex = exploreFilterBottomSheetTabIndex
        )
    }
}
