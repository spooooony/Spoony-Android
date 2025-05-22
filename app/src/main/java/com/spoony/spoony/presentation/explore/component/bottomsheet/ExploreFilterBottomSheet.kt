package com.spoony.spoony.presentation.explore.component.bottomsheet

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.bottomsheet.SpoonyBasicBottomSheet
import com.spoony.spoony.core.designsystem.component.button.SpoonyButton
import com.spoony.spoony.core.designsystem.component.chip.IconChip
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.type.ButtonSize
import com.spoony.spoony.core.designsystem.type.ButtonStyle
import com.spoony.spoony.core.util.extension.noRippleClickable
import com.spoony.spoony.presentation.explore.component.ExploreFilterChip
import com.spoony.spoony.presentation.explore.model.ExploreFilter
import com.spoony.spoony.presentation.explore.model.FilterType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreFilterBottomSheet(
    onDismiss: () -> Unit,
    onFilterReset: () -> Unit,
    onSave: () -> Unit,
    propertyItems: ImmutableList<ExploreFilter>,
    onToggleFilter: (Int, FilterType) -> Unit,
    categoryItems: ImmutableList<ExploreFilter>,
    regionItems: ImmutableList<ExploreFilter>,
    ageItems: ImmutableList<ExploreFilter>,
    propertySelectedState: SnapshotStateMap<Int, Boolean>,
    regionSelectedState: SnapshotStateMap<Int, Boolean>,
    categorySelectedState: SnapshotStateMap<Int, Boolean>,
    ageSelectedState: SnapshotStateMap<Int, Boolean>,
    modifier: Modifier = Modifier,
    tabIndex: Int = 0
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()

    val handleDismiss: () -> Unit = {
        scope.launch {
            sheetState.hide()
            onDismiss()
        }
    }

    SpoonyBasicBottomSheet(
        onDismiss = handleDismiss,
        sheetState = sheetState,
        modifier = modifier,
        dragHandle = { }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            ExploreFilterBottomSheetHeader(
                onDismiss = handleDismiss,
                onFilterReset = onFilterReset
            )
            Spacer(modifier = Modifier.height(19.dp))
            ExploreFilterBottomSheetContent(
                tabIndex = tabIndex,
                onFilterSelected = onToggleFilter,
                propertySelectedState = propertySelectedState,
                regionSelectedState = regionSelectedState,
                categorySelectedState = categorySelectedState,
                ageSelectedState = ageSelectedState,
                propertyItems = propertyItems,
                categoryItems = categoryItems,
                regionItems = regionItems,
                ageItems = ageItems
            )
            SpoonyButton(
                text = "필터 적용하기",
                onClick = onSave,
                style = ButtonStyle.Primary,
                size = ButtonSize.Xlarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 14.dp)
            )
        }
    }
}

@Composable
private fun ExploreFilterBottomSheetHeader(
    onDismiss: () -> Unit,
    onFilterReset: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "필터",
                modifier = Modifier.padding(start = 14.dp, end = 5.dp),
                style = SpoonyAndroidTheme.typography.body1b,
                color = SpoonyAndroidTheme.colors.gray900
            )
            Text(
                text = "필터 초기화",
                style = SpoonyAndroidTheme.typography.caption1m,
                color = SpoonyAndroidTheme.colors.gray400,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .padding(vertical = 4.dp, horizontal = 8.dp)
                    .noRippleClickable(onClick = onFilterReset)
            )
        }
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_close_24),
            contentDescription = null,
            tint = SpoonyAndroidTheme.colors.gray400,
            modifier = Modifier
                .padding(
                    top = 4.dp,
                    bottom = 4.dp,
                    end = 20.dp
                )
                .noRippleClickable(onClick = onDismiss)
        )
    }
}

private enum class FilterSectionMap(val tabIndex: Int, val headerIndex: Int) {
    PROPERTY(0, 0),
    CATEGORY(1, 2),
    REGION(2, 4),
    AGE(3, 6);

    companion object {
        fun fromTabIndex(index: Int): FilterSectionMap? =
            entries.firstOrNull { it.tabIndex == index }

        fun fromHeaderIndex(index: Int): FilterSectionMap? =
            entries.firstOrNull { it.headerIndex == index }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ExploreFilterBottomSheetContent(
    tabIndex: Int,
    onFilterSelected: (Int, FilterType) -> Unit,
    propertyItems: ImmutableList<ExploreFilter>,
    categoryItems: ImmutableList<ExploreFilter>,
    regionItems: ImmutableList<ExploreFilter>,
    ageItems: ImmutableList<ExploreFilter>,
    propertySelectedState: SnapshotStateMap<Int, Boolean>,
    regionSelectedState: SnapshotStateMap<Int, Boolean>,
    categorySelectedState: SnapshotStateMap<Int, Boolean>,
    ageSelectedState: SnapshotStateMap<Int, Boolean>
) {
    var selectedTabIndex by remember { mutableIntStateOf(tabIndex) }
    val tabs = remember { persistentListOf("속성", "카테고리", "지역", "연령대") }
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var isProgrammaticScrollInProgress by remember { mutableStateOf(false) }

    LaunchedEffect(selectedTabIndex) {
        FilterSectionMap.fromTabIndex(selectedTabIndex)?.let { section ->
            try {
                isProgrammaticScrollInProgress = true
                lazyListState.animateScrollToItem(section.headerIndex)
            } finally {
                isProgrammaticScrollInProgress = false
            }
        }
    }
    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.firstVisibleItemIndex }
            .distinctUntilChanged()
            .collect { index ->
                if (!isProgrammaticScrollInProgress) {
                    FilterSectionMap.fromHeaderIndex(index)?.let { section ->
                        selectedTabIndex = section.tabIndex
                    }
                }
            }
    }

    ExploreFilterBottomSheetTabRow(
        tabs = tabs,
        tabIndex = selectedTabIndex,
        onTabSelected = { index ->
            selectedTabIndex = index
            coroutineScope.launch {
                lazyListState.animateScrollToItem(index)
            }
        }
    )
    LazyColumn(
        state = lazyListState,
        modifier = Modifier
            .fillMaxWidth()
            .height(340.dp)
            .padding(
                vertical = 18.dp,
                horizontal = 12.dp
            )
    ) {
        item {
            FilterSectionHeader(title = "속성")
        }

        item {
            propertyItems.forEach { item ->
                ExploreFilterChip(
                    text = item.name,
                    isSelected = propertySelectedState[item.id] == true,
                    onClick = { onFilterSelected(item.id, FilterType.LOCAL_REVIEW) },
                    modifier = Modifier.padding(top = 12.dp, bottom = 24.dp)
                )
            }
        }

        item {
            FilterSectionHeader(title = "카테고리")
        }

        item {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 24.dp)
            ) {
                categoryItems.forEach { item ->
                    IconChip(
                        text = item.name,
                        onClick = { onFilterSelected(item.id, FilterType.CATEGORY) },
                        isSelected = categorySelectedState[item.id] == true,
                        unSelectedIconUrl = item.unSelectedIconUrl,
                        selectedIconUrl = item.selectedIconUrl,
                        textStyle = SpoonyAndroidTheme.typography.caption1m
                    )
                }
            }
        }

        item {
            FilterSectionHeader(title = "지역")
        }

        item {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 24.dp)
            ) {
                regionItems.forEach { item ->
                    ExploreFilterChip(
                        text = item.name,
                        isSelected = regionSelectedState[item.id] == true,
                        onClick = { onFilterSelected(item.id, FilterType.REGION) }
                    )
                }
            }
        }

        item {
            FilterSectionHeader(title = "연령대")
        }

        item {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                ageItems.forEach { item ->
                    ExploreFilterChip(
                        text = item.name,
                        isSelected = ageSelectedState[item.id] == true,
                        onClick = { onFilterSelected(item.id, FilterType.AGE) }
                    )
                }
            }
        }

        item { Spacer(modifier = Modifier.height(85.dp)) }
    }
}

@Composable
private fun ExploreFilterBottomSheetTabRow(
    tabs: ImmutableList<String>,
    onTabSelected: (Int) -> Unit,
    tabIndex: Int = 0
) {
    TabRow(
        selectedTabIndex = tabIndex,
        containerColor = SpoonyAndroidTheme.colors.white,
        contentColor = SpoonyAndroidTheme.colors.main400,
        indicator = { tabPositions ->
            if (tabPositions.isNotEmpty() && tabIndex < tabPositions.size) {
                val tabPosition = tabPositions[tabIndex]
                val tabWidth = tabPosition.right - tabPosition.left
                val indicatorOffset by animateDpAsState(
                    targetValue = tabPosition.left + (tabWidth - 50.dp) / 2,
                    label = "TabIndicatorOffset"
                )
                Box(
                    Modifier
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.BottomStart)
                        .offset(x = indicatorOffset)
                        .width(50.dp)
                        .height(2.dp)
                        .background(color = SpoonyAndroidTheme.colors.main400)
                )
            }
        },
        divider = {
            HorizontalDivider(
                color = SpoonyAndroidTheme.colors.gray200,
                thickness = 1.dp
            )
        }
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                content = {
                    Text(
                        text = title,
                        style = SpoonyAndroidTheme.typography.body2b,
                        color = if (tabIndex == index) SpoonyAndroidTheme.colors.main400 else SpoonyAndroidTheme.colors.gray400
                    )
                },
                selected = tabIndex == index,
                onClick = {
                    onTabSelected(index)
                },
                selectedContentColor = Color.White,
                modifier = Modifier
                    .padding(bottom = 7.dp)
            )
        }
    }
}

@Composable
private fun FilterSectionHeader(
    title: String
) {
    Text(
        text = title,
        style = SpoonyAndroidTheme.typography.body1b,
        color = SpoonyAndroidTheme.colors.gray900,
        modifier = Modifier.fillMaxWidth()
    )
}
