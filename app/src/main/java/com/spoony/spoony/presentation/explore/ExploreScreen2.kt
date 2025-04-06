package com.spoony.spoony.presentation.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.presentation.explore.component.ExploreTabRow
import com.spoony.spoony.presentation.explore.component.FilterChipRow
import com.spoony.spoony.presentation.explore.model.FilterChip
import com.spoony.spoony.presentation.explore.model.FilterChipDataProvider
import okhttp3.internal.immutableListOf

@Composable
fun ExploreRoute2() {
    val tabList = immutableListOf("전체", "팔로잉")
    val selectedTabIndex = remember { mutableIntStateOf(0) }
    val chipItems = FilterChipDataProvider.getDefaultFilterChips()
    ExploreScreen2(
        tabList = tabList,
        chipItems = chipItems,
        selectedTabIndex = selectedTabIndex
    )
}

@Composable
fun ExploreScreen2(
    tabList: List<String>,
    chipItems: List<FilterChip>,
    selectedTabIndex: MutableState<Int>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SpoonyAndroidTheme.colors.white)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ExploreTabRow(
                selectedTabIndex = selectedTabIndex,
                tabList = tabList
            )
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_search_20),
                modifier = Modifier.size(20.dp),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        FilterChipRow(
            chipItems
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Preview
@Composable
private fun ExploreScreenPreview() {
    val tabList = immutableListOf("전체", "팔로잉")
    val selectedTabIndex = remember { mutableIntStateOf(0) }
    val chipItems = FilterChipDataProvider.getDefaultFilterChips()
    SpoonyAndroidTheme {
        Column() {
            ExploreScreen2(
                tabList = tabList,
                selectedTabIndex = selectedTabIndex,
                chipItems = chipItems
            )
        }
    }
}
