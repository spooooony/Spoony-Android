package com.spoony.spoony.presentation.explore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.spoony.spoony.presentation.explore.component.bottomsheet.ExploreSortingBottomSheet

@Composable
fun ExploreRoute(
    paddingValues: PaddingValues
) {
    ExploreScreen()
}

@Composable
fun ExploreScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ExploreSortingBottomSheet(
            onDismiss = {},
            onClick = {}
        )
    }
}
