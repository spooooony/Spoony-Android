package com.spoony.spoony.presentation.map.search

import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.presentation.map.model.LocationModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class MapSearchState(
    val searchKeyword: String = "",
    val locationModelList: UiState<ImmutableList<LocationModel>> = UiState.Loading,
    val recentSearchQueryList: ImmutableList<String> = persistentListOf()
)
