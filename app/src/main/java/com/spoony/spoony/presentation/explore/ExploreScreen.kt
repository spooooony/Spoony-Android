package com.spoony.spoony.presentation.explore

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.spoony.spoony.presentation.explore.component.ExploreTopAppBar
import com.spoony.spoony.presentation.explore.component.bottomsheet.ExploreLocationBottomSheet

@Composable
fun ExploreRoute(
    paddingValues: PaddingValues
) {
    ExploreScreen(
        spoonCount = 99,
        selectedCity = "마포구",
        onLocationSortingButtonClick = {}
    )
}

@Composable
fun ExploreScreen(
    spoonCount: Int,
    selectedCity: String,
    onLocationSortingButtonClick: (String) -> Unit,
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
                },
            )
        }
    }
}
