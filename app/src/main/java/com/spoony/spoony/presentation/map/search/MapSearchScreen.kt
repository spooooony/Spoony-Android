package com.spoony.spoony.presentation.map.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.core.util.extension.noRippleClickable
import com.spoony.spoony.presentation.map.search.component.MapSearchEmptyResultScreen
import com.spoony.spoony.presentation.map.search.component.MapSearchRecentEmptyScreen
import com.spoony.spoony.presentation.map.search.component.MapSearchRecentItem
import com.spoony.spoony.presentation.map.search.component.MapSearchResultItem
import com.spoony.spoony.presentation.map.search.component.MapSearchTopAppBar
import com.spoony.spoony.presentation.map.search.model.LocationModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun MapSearchRoute(
    viewModel: MapSearchViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MapSearchScreen(
        searchKeyword = state.searchKeyword,
        recentSearchList = persistentListOf(),
        locationModelList = state.locationModelList,
        onSearchKeywordChanged = viewModel::updateSearchKeyword,
        onSearchButtonClick = {},
        onDeleteButtonClick = {},
        onResultItemClick = {}
    )
}

@Composable
private fun MapSearchScreen(
    searchKeyword: String,
    recentSearchList: ImmutableList<String>,
    locationModelList: UiState<ImmutableList<LocationModel>>,
    onSearchKeywordChanged: (String) -> Unit,
    onSearchButtonClick: (String) -> Unit,
    onDeleteButtonClick: () -> Unit,
    onResultItemClick: (Int) -> Unit
) {
    Column {
        MapSearchTopAppBar(
            value = searchKeyword,
            onValueChanged = onSearchKeywordChanged,
            onSearchAction = { onSearchButtonClick(searchKeyword) }
        )

        when {
            searchKeyword.isBlank() -> {
                if (recentSearchList.isEmpty()) {
                    MapSearchRecentEmptyScreen()
                } else {
                    LazyColumn {
                        items(
                            items = recentSearchList,
                            key = { it }
                        ) { searchKeyword ->
                            MapSearchRecentItem(
                                searchText = searchKeyword,
                                onClick = onDeleteButtonClick
                            )
                        }
                    }
                }
            }

            else -> {
                when (locationModelList) {
                    is UiState.Success -> {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(1.dp),
                            modifier = Modifier
                                .padding(horizontal = 20.dp)
                                .background(SpoonyAndroidTheme.colors.gray0)
                        ) {
                            items(
                                items = locationModelList.data,
                                key = { locationInfo ->
                                    locationInfo.locationId
                                }
                            ) { locationInfo ->
                                MapSearchResultItem(
                                    placeName = locationInfo.locationName,
                                    address = locationInfo.locationAddress,
                                    modifier = Modifier
                                        .background(SpoonyAndroidTheme.colors.white)
                                        .noRippleClickable { onResultItemClick(locationInfo.locationId) }
                                )
                            }
                        }
                    }

                    is UiState.Empty -> {
                        MapSearchEmptyResultScreen()
                    }

                    else -> {}
                }
            }
        }
    }
}
