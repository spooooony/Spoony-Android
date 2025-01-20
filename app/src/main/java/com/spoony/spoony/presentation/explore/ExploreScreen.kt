package com.spoony.spoony.presentation.explore

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.chip.IconChip
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.type.ChipColor
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.core.util.extension.noRippleClickable
import com.spoony.spoony.domain.entity.CategoryEntity
import com.spoony.spoony.presentation.explore.component.ExploreEmptyScreen
import com.spoony.spoony.presentation.explore.component.ExploreItem
import com.spoony.spoony.presentation.explore.component.ExploreTopAppBar
import com.spoony.spoony.presentation.explore.component.bottomsheet.ExploreLocationBottomSheet
import com.spoony.spoony.presentation.explore.component.bottomsheet.ExploreSortingBottomSheet
import com.spoony.spoony.presentation.explore.model.FeedModel
import com.spoony.spoony.presentation.explore.type.SortingOption
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ExploreRoute(
    paddingValues: PaddingValues,
    viewModel: ExploreViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    val spoonCount = when (state.value.spoonCount) {
        is UiState.Success -> (state.value.spoonCount as? UiState.Success<Int>)?.data ?: 0
        else -> 0
    }

    val categoryList = when (state.value.categoryList) {
        is UiState.Success -> (state.value.categoryList as? UiState.Success<ImmutableList<CategoryEntity>>)?.data ?: persistentListOf()
        else -> persistentListOf()
    }

    with(state.value) {
        ExploreScreen(
            paddingValues = paddingValues,
            spoonCount = spoonCount,
            selectedCity = selectedCity,
            selectedCategoryId = selectedCategoryId,
            selectedSortingOption = selectedSortingOption,
            categoryList = categoryList,
            feedList = feedList,
            onLocationSortingButtonClick = viewModel::updateSelectedCity,
            onSortingButtonClick = viewModel::updateSelectedSortingOption,
            onFeedItemClick = {},
            onRegisterButtonClick = {},
            updateSelectedCategory = viewModel::updateSelectedCategory
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ExploreScreen(
    paddingValues: PaddingValues,
    spoonCount: Int,
    selectedCity: String,
    selectedCategoryId: Int,
    selectedSortingOption: SortingOption,
    categoryList: ImmutableList<CategoryEntity>,
    feedList: UiState<ImmutableList<FeedModel>>,
    onLocationSortingButtonClick: (String) -> Unit,
    onSortingButtonClick: (SortingOption) -> Unit,
    onFeedItemClick: (Int) -> Unit,
    onRegisterButtonClick: () -> Unit,
    updateSelectedCategory: (Int) -> Unit
) {
    var isLocationBottomSheetVisible by remember { mutableStateOf(false) }
    var isSortingBottomSheetVisible by remember { mutableStateOf(false) }

    if (isLocationBottomSheetVisible) {
        ExploreLocationBottomSheet(
            selectedCity = selectedCity,
            onDismiss = { isLocationBottomSheetVisible = false },
            onClick = onLocationSortingButtonClick
        )
    }

    if (isSortingBottomSheetVisible) {
        ExploreSortingBottomSheet(
            onDismiss = { isSortingBottomSheetVisible = false },
            onClick = onSortingButtonClick,
            currentSortingOption = selectedSortingOption
        )
    }

    Column(
        modifier = Modifier
            .padding(bottom = paddingValues.calculateBottomPadding())
    ) {
        ExploreTopAppBar(
            count = spoonCount,
            onClick = {
                isLocationBottomSheetVisible = true
            },
            place = selectedCity
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(
                items = categoryList,
                key = { category ->
                    category.categoryId
                }
            ) { category ->
                IconChip(
                    text = category.categoryName,
                    tagColor = if (selectedCategoryId == category.categoryId) ChipColor.Black else ChipColor.White,
                    iconUrl = category.iconUrl,
                    onClick = { updateSelectedCategory(category.categoryId) }
                )
            }
        }

        ExploreContent(
            feedList = feedList,
            selectedSortingOption = selectedSortingOption,
            onSortingButtonClick = { isSortingBottomSheetVisible = it },
            onFeedItemClick = onFeedItemClick,
            onRegisterButtonClick = onRegisterButtonClick
        )
    }
}

@Composable
private fun ExploreContent(
    feedList: UiState<ImmutableList<FeedModel>>,
    selectedSortingOption: SortingOption,
    onSortingButtonClick: (Boolean) -> Unit,
    onFeedItemClick: (Int) -> Unit,
    onRegisterButtonClick: () -> Unit
) {
    when (feedList) {
        is UiState.Empty -> {
            ExploreEmptyScreen(
                onClick = onRegisterButtonClick,
                modifier = Modifier
                    .fillMaxSize()
            )
        }

        is UiState.Success -> {
            Column {
                Box(
                    contentAlignment = Alignment.CenterEnd,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SpoonyAndroidTheme.colors.white)
                        .padding(vertical = 16.dp, horizontal = 20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .noRippleClickable { onSortingButtonClick(true) }
                            .padding(4.dp)
                    ) {
                        Text(
                            text = selectedSortingOption.stringValue,
                            style = SpoonyAndroidTheme.typography.caption1m,
                            color = SpoonyAndroidTheme.colors.gray700,
                            modifier = Modifier
                                .padding(end = 2.dp)
                        )

                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_filter_24),
                            contentDescription = null,
                            tint = SpoonyAndroidTheme.colors.gray700,
                            modifier = Modifier
                                .size(16.dp)
                        )
                    }
                }

                LazyColumn(
                    contentPadding = PaddingValues(bottom = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = feedList.data,
                        key = { feed ->
                            feed.feedId
                        }
                    ) { feed ->
                        ExploreItem(
                            username = feed.username,
                            placeSpoon = feed.userRegion,
                            review = feed.title,
                            addMapCount = feed.addMapCount,
                            iconUrl = feed.categoryEntity.iconUrl,
                            tagText = feed.categoryEntity.categoryName,
                            textColorHex = feed.categoryEntity.textColor ?: "000000",
                            backgroundColorHex = feed.categoryEntity.backgroundColor ?: "000000",
                            modifier = Modifier
                                .fillMaxWidth()
                                .noRippleClickable { onFeedItemClick(feed.feedId) }
                                .padding(horizontal = 20.dp)
                        )
                    }
                }
            }
        }

        else -> {}
    }
}
