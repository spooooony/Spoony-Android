package com.spoony.spoony.presentation.explore

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.chip.IconChip
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.type.ChipColor
import com.spoony.spoony.core.util.extension.noRippleClickable
import com.spoony.spoony.domain.entity.CategoryEntity
import com.spoony.spoony.presentation.explore.component.ExploreEmptyScreen
import com.spoony.spoony.presentation.explore.component.ExploreItem
import com.spoony.spoony.presentation.explore.component.ExploreTopAppBar
import com.spoony.spoony.presentation.explore.component.bottomsheet.ExploreLocationBottomSheet
import com.spoony.spoony.presentation.explore.component.bottomsheet.ExploreSortingBottomSheet
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ExploreRoute(
    paddingValues: PaddingValues
) {
    ExploreScreen(
        paddingValues = paddingValues,
        spoonCount = 99,
        selectedCity = "마포구",
        selectedCategory = 0,
        categoryList = persistentListOf(),
        feedList = persistentListOf(),
        onLocationSortingButtonClick = {},
        updateSelectedCategory = {}
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExploreScreen(
    paddingValues: PaddingValues,
    spoonCount: Int,
    selectedCity: String,
    selectedCategory: Int,
    categoryList: ImmutableList<CategoryEntity>,
    feedList: ImmutableList<FeedModel>,
    onLocationSortingButtonClick: (String) -> Unit,
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
            onClick = { },
            currentSortingOption = SortingOption.LATEST
        )
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())
    ) {
        stickyHeader {
            ExploreTopAppBar(
                count = spoonCount,
                onClick = {
                    isLocationBottomSheetVisible = true
                }
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
                        tagColor = if (selectedCategory == category.categoryId) ChipColor.Black else ChipColor.White,
                        iconUrl = category.iconUrl,
                        onClick = { updateSelectedCategory(category.categoryId) }
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SpoonyAndroidTheme.colors.white)
                    .padding(vertical = 16.dp, horizontal = 20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .noRippleClickable { isSortingBottomSheetVisible = true }
                        .padding(4.dp)
                ) {
                    Text(
                        text = "최신순",
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
        }
        if (feedList.isEmpty()) {
            item {
                ExploreEmptyScreen(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        } else {
            items(
                items = feedList,
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
                        .padding(horizontal = 20.dp)
                )
            }
        }
    }
}
