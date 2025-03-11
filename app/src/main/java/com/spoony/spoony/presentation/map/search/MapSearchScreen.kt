package com.spoony.spoony.presentation.map.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.core.util.extension.noRippleClickable
import com.spoony.spoony.presentation.map.map.model.LocationModel
import com.spoony.spoony.presentation.map.search.component.MapSearchEmptyResultScreen
import com.spoony.spoony.presentation.map.search.component.MapSearchRecentEmptyScreen
import com.spoony.spoony.presentation.map.search.component.MapSearchRecentItem
import com.spoony.spoony.presentation.map.search.component.MapSearchResultItem
import com.spoony.spoony.presentation.map.search.component.MapSearchTopAppBar
import kotlinx.collections.immutable.ImmutableList

@Composable
fun MapSearchRoute(
    navigateUp: () -> Unit,
    navigateToLocationMap: (Int, String, String, String, String) -> Unit,
    viewModel: MapSearchViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.searchKeyword.isEmpty()) {
        viewModel.getAllSearches()
    }

    MapSearchScreen(
        searchKeyword = state.searchKeyword,
        recentSearchList = state.recentSearchQueryList,
        locationModelList = state.locationModelList,
        onSearchButtonClick = viewModel::searchLocation,
        onSearchKeywordChanged = viewModel::updateSearchKeyword,
        onBackButtonClick = navigateUp,
        onDeleteButtonClick = viewModel::deleteSearchByText,
        onResultItemClick = navigateToLocationMap,
        onDeleteAllButtonClick = viewModel::deleteAllSearches
    )
}

@Composable
private fun MapSearchScreen(
    searchKeyword: String,
    recentSearchList: ImmutableList<String>,
    locationModelList: UiState<ImmutableList<LocationModel>>,
    onSearchKeywordChanged: (String) -> Unit,
    onSearchButtonClick: () -> Unit,
    onBackButtonClick: () -> Unit,
    onDeleteButtonClick: (String) -> Unit,
    onResultItemClick: (Int, String, String, String, String) -> Unit,
    onDeleteAllButtonClick: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column {
        MapSearchTopAppBar(
            value = searchKeyword,
            onValueChanged = onSearchKeywordChanged,
            onSearchAction = onSearchButtonClick,
            onBackButtonClick = onBackButtonClick,
            focusRequester = focusRequester
        )

        when {
            searchKeyword.isBlank() -> {
                if (recentSearchList.isEmpty()) {
                    MapSearchRecentEmptyScreen()
                } else {
                    Row(
                        modifier = Modifier
                            .padding(
                                top = 20.dp,
                                bottom = 16.dp,
                                start = 20.dp,
                                end = 20.dp
                            )
                    ) {
                        Text(
                            text = "최근 검색",
                            style = SpoonyAndroidTheme.typography.body2b,
                            color = SpoonyAndroidTheme.colors.gray700,
                            modifier = Modifier
                                .weight(1f)
                        )
                        Text(
                            text = "전체삭제",
                            style = SpoonyAndroidTheme.typography.caption1m,
                            color = SpoonyAndroidTheme.colors.gray500,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .noRippleClickable(onClick = onDeleteAllButtonClick)
                                .padding(horizontal = 8.dp)
                        )
                    }

                    LazyColumn(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                    ) {
                        items(
                            items = recentSearchList
                        ) { searchKeyword ->
                            MapSearchRecentItem(
                                searchText = searchKeyword,
                                onClickIcon = {
                                    onDeleteButtonClick(searchKeyword)
                                },
                                onClickText = {
                                    onSearchKeywordChanged(searchKeyword)
                                    onSearchButtonClick()
                                    focusManager.clearFocus()
                                }
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
                                items = locationModelList.data
                            ) { locationInfo ->
                                MapSearchResultItem(
                                    placeName = locationInfo.placeName ?: "",
                                    address = locationInfo.locationAddress ?: "",
                                    modifier = Modifier
                                        .background(SpoonyAndroidTheme.colors.white)
                                        .noRippleClickable {
                                            onResultItemClick(
                                                locationInfo.placeId ?: 0,
                                                locationInfo.placeName ?: "",
                                                locationInfo.scale.toString(),
                                                locationInfo.latitude.toString(),
                                                locationInfo.longitude.toString()
                                            )
                                        }
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
