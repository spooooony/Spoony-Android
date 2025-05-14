package com.spoony.spoony.presentation.exploreSearch

import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.presentation.exploreSearch.model.ExploreSearchPlaceReviewModel
import com.spoony.spoony.presentation.exploreSearch.model.ExploreSearchUserModel
import com.spoony.spoony.presentation.exploreSearch.type.SearchType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class ExploreSearchState(
    val searchKeyword: String = "",
    val recentReviewSearchQueryList: ImmutableList<String> = persistentListOf(),
    val recentUserSearchQueryList: ImmutableList<String> = persistentListOf(),
    val searchType: SearchType = SearchType.USER,
    val userInfoList: UiState<ImmutableList<ExploreSearchUserModel>> = UiState.Loading,
    val placeReviewInfoList: UiState<ImmutableList<ExploreSearchPlaceReviewModel>> = UiState.Loading
)
