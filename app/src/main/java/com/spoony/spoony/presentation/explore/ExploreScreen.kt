package com.spoony.spoony.presentation.explore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.component.chip.IconChip
import com.spoony.spoony.core.designsystem.type.ChipColor
import com.spoony.spoony.domain.entity.CategoryEntity
import com.spoony.spoony.presentation.explore.component.ExploreTopAppBar
import com.spoony.spoony.presentation.explore.component.bottomsheet.ExploreLocationBottomSheet
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ExploreRoute(
    paddingValues: PaddingValues
) {
    ExploreScreen(
        spoonCount = 99,
        selectedCity = "마포구",
        selectedCategory = 0,
        categoryList = persistentListOf(),
        onLocationSortingButtonClick = {},
        updateSelectedCategory = {}
    )
}

@Composable
fun ExploreScreen(
    spoonCount: Int,
    selectedCity: String,
    selectedCategory: Int,
    categoryList: ImmutableList<CategoryEntity>,
    onLocationSortingButtonClick: (String) -> Unit,
    updateSelectedCategory: (Int) -> Unit
) {
    var isLocationBottomSheetVisible by remember { mutableStateOf(false) }

    if (isLocationBottomSheetVisible) {
        ExploreLocationBottomSheet(
            selectedCity = selectedCity,
            onDismiss = { isLocationBottomSheetVisible = false },
            onClick = onLocationSortingButtonClick
        )
    }

    LazyColumn {
        item {
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
        }
    }
}
