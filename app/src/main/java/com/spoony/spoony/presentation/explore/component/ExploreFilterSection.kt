package com.spoony.spoony.presentation.explore.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.spoony.spoony.core.analytics.events.LocalTracker
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
    val tracker = LocalTracker.current

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
                    onLocalReviewButtonClick = {
                        tracker.commonEvents.filterApplied(
                            pageApplied = "explore",
                            localReviewFilter = !(selectedFilterState.properties[2] ?: false)
                        )

                        onAction(ExploreAction.ClickLocalReview)
                    },
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
            onClick = { sortType ->
                onAction(ExploreAction.ChangeSorting(sortType))
                tracker.exploreEvents.sortSelected(sortType.trackingCode)
            },
            currentSortingOption = selectedSortingOption
        )
    }

    if (isFilterBottomSheetVisible) {
        ExploreFilterBottomSheet(
            onDismiss = { isFilterBottomSheetVisible = false },
            onFilterReset = { tempFilterState.reset() },
            onSave = {
                isFilterBottomSheetVisible = false

                val categoryFilters = filterItems.categories.filter { tempFilterState.categories[it.id] == true }.map { it.name }
                val regionFilters = filterItems.regions.filter { tempFilterState.regions[it.id] == true }.map { it.name }
                val ageGroupFilters = filterItems.ages.filter { tempFilterState.ages[it.id] == true }.map { it.name }
                val isLocalReviewEnabled = filterItems.properties.firstOrNull()?.let { tempFilterState.properties[it.id] == true } ?: false

                tracker.commonEvents.filterApplied(
                    pageApplied = "explore",
                    localReviewFilter = isLocalReviewEnabled
                )

                if (categoryFilters.isNotEmpty() || regionFilters.isNotEmpty() || ageGroupFilters.isNotEmpty()) {
                    tracker.exploreEvents.exploreFilterApplied(
                        categoryFilters = categoryFilters,
                        regionFilters = regionFilters,
                        ageGroupFilters = ageGroupFilters
                    )
                }

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
