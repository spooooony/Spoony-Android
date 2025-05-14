package com.spoony.spoony.presentation.explore

import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.domain.entity.CategoryEntity
import com.spoony.spoony.presentation.explore.model.ExploreFilter
import com.spoony.spoony.presentation.explore.model.ExploreFilterDataProvider
import com.spoony.spoony.presentation.explore.model.FilterChipOptionProvider
import com.spoony.spoony.presentation.explore.model.FilterOption
import com.spoony.spoony.presentation.explore.model.PlaceReviewModel
import com.spoony.spoony.presentation.explore.type.SortingOption
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentMapOf

data class ExploreState(
    val selectedCategoryId: Int = 1,
    val selectedSortingOption: SortingOption = SortingOption.LATEST,
    val chipItems: ImmutableList<FilterOption> = FilterChipOptionProvider.getDefaultFilterOptions(),
    val categoryList: UiState<ImmutableList<CategoryEntity>> = UiState.Loading,
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

data class ExploreFilterItems(
    val properties: ImmutableList<ExploreFilter>,
    val categories: ImmutableList<ExploreFilter>,
    val regions: ImmutableList<ExploreFilter>,
    val ages: ImmutableList<ExploreFilter>
)

enum class ExploreType {
    ALL, FOLLOWING
}
