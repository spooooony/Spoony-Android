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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
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
import com.spoony.spoony.presentation.explore.model.ExploreFilterDataProvider
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreFilterBottomSheet(
    onDismiss: () -> Unit,
    onFilterReset: () -> Unit,
    onSave: () -> Unit,
    propertyItems: MutableList<ExploreFilter>,
    categoryItems: MutableList<ExploreFilter>,
    regionItems: MutableList<ExploreFilter>,
    ageItems: MutableList<ExploreFilter>,
    selectedState: SnapshotStateMap<Int, Boolean>,
    filterIds: MutableList<Int>,
    modifier: Modifier = Modifier
) {
    val tabIndex by remember { mutableIntStateOf(0) }
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
                .padding(bottom = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            ExploreFilterBottomSheetHeader(
                onDismiss = handleDismiss,
                onFilterReset = {
                    onFilterReset()
                }
            )
            Spacer(modifier = Modifier.height(19.dp))
            ExploreFilterBottomSheetContent(
                tabIndex = tabIndex,
                onFilterSelected = { id ->
                    when (filterIds.contains(id)) {
                        true -> filterIds.remove(id)
                        else -> filterIds.add(id)
                    }
                },
                selectedState = selectedState,
                propertyItems = propertyItems,
                categoryItems = categoryItems,
                regionItems = regionItems,
                ageItems = ageItems
            )
            SpoonyButton(
                text = "필터 적용하기",
                onClick = {
                    handleDismiss()
                    onSave()
                },
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
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(14.dp))
            Text(
                text = "필터",
                style = SpoonyAndroidTheme.typography.body1b,
                color = SpoonyAndroidTheme.colors.gray900
            )
            Spacer(modifier = Modifier.width(13.dp))
            Text(
                text = "필터 초기화",
                style = SpoonyAndroidTheme.typography.caption1m,
                color = SpoonyAndroidTheme.colors.gray400,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.noRippleClickable(onClick = onFilterReset)
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

@Composable
private fun ExploreFilterBottomSheetTabRow(
    onTabSelected: (Int) -> Unit,
    tabIndex: Int = 0,
    tabs: ImmutableList<String> = persistentListOf("속성", "카테고리", "지역", "연령대")
) {
    var selectedTabIndex by remember { mutableIntStateOf(tabIndex) }
    TabRow(
        selectedTabIndex = selectedTabIndex,
        containerColor = SpoonyAndroidTheme.colors.white,
        contentColor = SpoonyAndroidTheme.colors.main400,
        indicator = { tabPositions ->
            if (tabPositions.isNotEmpty() && selectedTabIndex < tabPositions.size) {
                val tabPosition = tabPositions[selectedTabIndex]
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
                        color = if (selectedTabIndex == index) SpoonyAndroidTheme.colors.main400 else SpoonyAndroidTheme.colors.gray400
                    )
                },
                selected = selectedTabIndex == index,
                onClick = {
                    selectedTabIndex = index
                    onTabSelected(index)
                },
                selectedContentColor = Color.White,
                modifier = Modifier
                    .padding(bottom = 7.dp)
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExploreFilterBottomSheetContent(
    tabIndex: Int,
    onFilterSelected: (Int) -> Unit,
    propertyItems: MutableList<ExploreFilter>,
    categoryItems: MutableList<ExploreFilter>,
    regionItems: MutableList<ExploreFilter>,
    ageItems: MutableList<ExploreFilter>,
    selectedState: SnapshotStateMap<Int, Boolean>
) {
    var selectedTabIndex by remember { mutableIntStateOf(tabIndex) }
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val propertySectionIndex = 0
    val categorySectionIndex = 1
    val regionSectionIndex = 2
    val ageSectionIndex = 3
    LaunchedEffect(selectedTabIndex) {
        val targetIndex = when (selectedTabIndex) {
            0 -> propertySectionIndex
            1 -> categorySectionIndex
            2 -> regionSectionIndex
            3 -> ageSectionIndex
            else -> 0
        }

        coroutineScope.launch {
            lazyListState.animateScrollToItem(targetIndex)
        }
    }
    ExploreFilterBottomSheetTabRow(
        tabIndex = selectedTabIndex,
        onTabSelected = { index ->
            selectedTabIndex = index
        }
    )
    LazyColumn(
        state = lazyListState,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 340.dp, max = 340.dp)
            .padding(
                vertical = 18.dp,
                horizontal = 12.dp
            ),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item(key = "property_content") {
            FilterSectionHeader(title = "속성")
            propertyItems.forEach { item ->
                ExploreFilterChip(
                    text = item.name,
                    isSelected = selectedState[item.id] == true,
                    onClick = {
                        selectedState[item.id] = !(selectedState[item.id] ?: false)
                        onFilterSelected(item.id)
                    }
                )
            }
        }

        item(key = "category_content") {
            FilterSectionHeader(title = "카테고리")
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                categoryItems.forEach { item ->
                    IconChip(
                        text = item.name,
                        onClick = {
                            selectedState[item.id] = !(selectedState[item.id] ?: false)
                            onFilterSelected(item.id)
                        },
                        isSelected = selectedState[item.id] == true,
                        unSelectedIconUrl = item.unSelectedIconUrl,
                        selectedIconUrl = item.selectedIconUrl,
                        textStyle = SpoonyAndroidTheme.typography.caption1m
                    )
                }
            }
        }

        item(key = "region_content") {
            FilterSectionHeader(title = "지역")
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                regionItems.forEach { item ->
                    ExploreFilterChip(
                        text = item.name,
                        isSelected = selectedState[item.id] == true,
                        onClick = {
                            selectedState[item.id] = !(selectedState[item.id] ?: false)
                            onFilterSelected(item.id)
                        }
                    )
                }
            }
        }

        item(key = "age_content") {
            FilterSectionHeader(title = "정렬")
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                ageItems.forEach { item ->
                    ExploreFilterChip(
                        text = item.name,
                        isSelected = selectedState[item.id] == true,
                        onClick = {
                            selectedState[item.id] = !(selectedState[item.id] ?: false)
                            onFilterSelected(item.id)
                        }
                    )
                }
            }
        }
        item(key = "bottom_spacer") { Spacer(modifier = Modifier.height(85.dp)) }
    }
}

@Composable
fun FilterSectionHeader(
    title: String
) {
    Text(
        text = title,
        style = SpoonyAndroidTheme.typography.body1b,
        color = SpoonyAndroidTheme.colors.gray900,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
    )
}

/**
 *  Summarize selected filters by their type and name
 *  output example:
 *  {
 *      "property" : "로컬 리뷰",
 *      "region" : "강남구 외 2개",
 *      "age" : "20대",
 *      "category" : "한식 외 1개"
 *  }
 */
private fun summarizeFilters(
    filterIds: List<Int>,
    allFilters: List<ExploreFilter>
): Map<String, String> {
    val sortedFilterIds = filterIds.sorted()
    val selectedFilters = sortedFilterIds.mapNotNull { id -> allFilters.find { it.id == id } }
    val groupedByType = selectedFilters.groupBy { it.type }

    return groupedByType.mapValues { (_, filters) ->
        if (filters.size == 1) {
            filters.first().name
        } else {
            "${filters.first().name} 외 ${filters.size - 1}개"
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ExploreFilterBottomSheetPreview() {
    var isDialogVisible by remember { mutableStateOf(false) }
    val filterIds = remember { mutableStateListOf<Int>() }
    val filterIdsBackup = remember { filterIds.toMutableStateList() }

    val propertyItems = remember { ExploreFilterDataProvider.getDefaultPropertyFilter() }
    val regionItems = remember { ExploreFilterDataProvider.getDefaultRegionFilter() }
    val ageItems = remember { ExploreFilterDataProvider.getDefaultAgeFilter() }
    val categoryItems = remember { ExploreFilterDataProvider.getDefaultCategoryFilter() }
    val selectedState = remember { mutableStateMapOf<Int, Boolean>() }
    val allFilters = propertyItems + categoryItems + regionItems + ageItems
    allFilters.forEach { filter ->
        selectedState[filter.id] = filter.id in filterIdsBackup
    }

    /**
     * Save changes to filterIdsBackup and show the summary of selected filters
     */
    val saveChanges: () -> Unit = {
        filterIds.clear()
        filterIds.addAll(filterIdsBackup)
        val summary = summarizeFilters(filterIds, allFilters)

        summary.forEach { (type, text) ->
            Timber.tag("ExploreFilterBottomSheet").d("$type : $text")
        }
    }

    /**
     * Return changes to filterIdsBackup and update the selected state
     */
    val returnChanges: () -> Unit = {
        filterIdsBackup.clear()
        filterIdsBackup.addAll(filterIds)
        allFilters.forEach { filter ->
            selectedState[filter.id] = filter.id in filterIds
        }
    }

    /**
     * Reset all filters and update the selected state
     */
    val resetFilters: () -> Unit = {
        filterIdsBackup.clear()
        allFilters.forEach { filter ->
            selectedState[filter.id] = false
        }
    }

    SpoonyAndroidTheme {
        if (isDialogVisible) {
            ExploreFilterBottomSheet(
                onDismiss = {
                    isDialogVisible = false
                    returnChanges()
                },
                onFilterReset = resetFilters,
                onSave = saveChanges,
                propertyItems = propertyItems.toMutableStateList(),
                regionItems = regionItems.toMutableStateList(),
                ageItems = ageItems.toMutableStateList(),
                categoryItems = categoryItems.toMutableStateList(),
                selectedState = selectedState,
                filterIds = filterIdsBackup
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Button(
                onClick = { isDialogVisible = true }
            ) {
                Text(text = "bottomSheet")
            }
        }
    }
}
