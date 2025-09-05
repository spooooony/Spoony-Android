package com.spoony.spoony.presentation.explore

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.presentation.explore.extension.toMutableStateMap
import com.spoony.spoony.presentation.explore.model.ExploreFilter
import com.spoony.spoony.presentation.explore.model.ExploreFilterDataProvider
import com.spoony.spoony.presentation.explore.model.FilterChipOptionProvider
import com.spoony.spoony.presentation.explore.model.FilterOption
import com.spoony.spoony.presentation.explore.model.PlaceReviewModel
import com.spoony.spoony.presentation.explore.type.SortingOption
import com.spoony.spoony.presentation.register.model.RegisterType
import com.spoony.spoony.presentation.report.ReportType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toPersistentMap

data class ExploreState(
    val selectedCategoryId: Int = 1,
    val selectedSortingOption: SortingOption = SortingOption.LATEST,
    val chipItems: ImmutableList<FilterOption> = FilterChipOptionProvider.getDefaultFilterOptions(),
    val placeReviewList: UiState<ImmutableList<PlaceReviewModel>> = UiState.Loading,
    val exploreType: ExploreType = ExploreType.ALL,
    val filterSelectionState: ExploreFilterState = ExploreFilterState(
        properties = persistentMapOf(),
        categories = persistentMapOf(),
        regions = persistentMapOf(),
        ages = persistentMapOf()
    ),
    val exploreFilterItems: ExploreFilterItems = ExploreFilterItems(
        properties = ExploreFilterDataProvider.getDefaultPropertyFilter(),
        categories = ExploreFilterDataProvider.getDefaultCategoryFilter(),
        regions = ExploreFilterDataProvider.getDefaultRegionFilter(),
        ages = ExploreFilterDataProvider.getDefaultAgeFilter()
    )
)

data class ExploreFilterState(
    val properties: PersistentMap<Int, Boolean>,
    val categories: PersistentMap<Int, Boolean>,
    val regions: PersistentMap<Int, Boolean>,
    val ages: PersistentMap<Int, Boolean>
)

fun ExploreFilterState.toMutable(): MutableExploreFilterState {
    return MutableExploreFilterState(
        properties = properties.toMutableStateMap(),
        categories = categories.toMutableStateMap(),
        regions = regions.toMutableStateMap(),
        ages = ages.toMutableStateMap()
    )
}

data class MutableExploreFilterState(
    val properties: SnapshotStateMap<Int, Boolean> = mutableStateMapOf(),
    val categories: SnapshotStateMap<Int, Boolean> = mutableStateMapOf(),
    val regions: SnapshotStateMap<Int, Boolean> = mutableStateMapOf(),
    val ages: SnapshotStateMap<Int, Boolean> = mutableStateMapOf()
) {
    fun toPersistent(): ExploreFilterState {
        return ExploreFilterState(
            properties = properties.toPersistentMap(),
            categories = categories.toPersistentMap(),
            regions = regions.toPersistentMap(),
            ages = ages.toPersistentMap()
        )
    }

    fun reset() {
        properties.clear()
        categories.clear()
        regions.clear()
        ages.clear()
    }
}

data class ExploreFilterItems(
    val properties: ImmutableList<ExploreFilter>,
    val categories: ImmutableList<ExploreFilter>,
    val regions: ImmutableList<ExploreFilter>,
    val ages: ImmutableList<ExploreFilter>
)

sealed interface ExploreAction {
    data object ClickSearch : ExploreAction
    data object ClickRegister : ExploreAction
    data class ClickPlaceDetail(val id: Int) : ExploreAction
    data class ClickReport(val targetId: Int, val type: ReportType) : ExploreAction
    data class ClickEdit(val id: Int, val type: RegisterType) : ExploreAction
    data object ClickLocalReview : ExploreAction
    data class ChangeSorting(val option: SortingOption) : ExploreAction
    data class ChangeTab(val type: ExploreType) : ExploreAction
    data object Refresh : ExploreAction
    data object LoadNextPage : ExploreAction
    data class DeleteReview(val id: Int) : ExploreAction
    data class ApplyFilter(val state: ExploreFilterState) : ExploreAction
}

enum class ExploreType {
    ALL, FOLLOWING
}
