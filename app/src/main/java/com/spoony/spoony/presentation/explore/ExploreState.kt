package com.spoony.spoony.presentation.explore

import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.domain.entity.CategoryEntity
import com.spoony.spoony.presentation.explore.model.FeedModel
import com.spoony.spoony.presentation.explore.model.FilterChipOptionProvider
import com.spoony.spoony.presentation.explore.model.FilterOption
import com.spoony.spoony.presentation.explore.model.PlaceReviewModel
import com.spoony.spoony.presentation.explore.type.SortingOption
import kotlinx.collections.immutable.ImmutableList

data class ExploreState(
    val spoonCount: UiState<Int> = UiState.Loading,
    val selectedCity: String = "마포구",
    val selectedCategoryId: Int = 1,
    val selectedSortingOption: SortingOption = SortingOption.LATEST,
    val chipItems: ImmutableList<FilterOption> = FilterChipOptionProvider.getDefaultFilterOptions(),
    val categoryList: UiState<ImmutableList<CategoryEntity>> = UiState.Loading,
    val feedList: UiState<ImmutableList<FeedModel>> = UiState.Loading,
    val placeReviewList: UiState<ImmutableList<PlaceReviewModel>> = UiState.Loading
)
