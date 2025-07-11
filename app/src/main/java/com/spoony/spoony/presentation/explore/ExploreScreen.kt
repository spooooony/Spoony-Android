package com.spoony.spoony.presentation.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.card.ReviewCard
import com.spoony.spoony.core.designsystem.component.dialog.TwoButtonDialog
import com.spoony.spoony.core.designsystem.component.pullToRefresh.SpoonyPullToRefreshContainer
import com.spoony.spoony.core.designsystem.event.LocalSnackBarTrigger
import com.spoony.spoony.core.designsystem.model.ReviewCardCategory
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.core.util.extension.noRippleClickable
import com.spoony.spoony.presentation.explore.component.ExploreEmptyScreen
import com.spoony.spoony.presentation.explore.component.ExploreTabRow
import com.spoony.spoony.presentation.explore.component.FilterChipRow
import com.spoony.spoony.presentation.explore.component.bottomsheet.ExploreFilterBottomSheet
import com.spoony.spoony.presentation.explore.component.bottomsheet.ExploreSortingBottomSheet
import com.spoony.spoony.presentation.explore.model.ExploreFilter
import com.spoony.spoony.presentation.explore.model.FilterOption
import com.spoony.spoony.presentation.explore.model.FilterType
import com.spoony.spoony.presentation.explore.model.PlaceReviewModel
import com.spoony.spoony.presentation.explore.type.ExploreDropdownOption
import com.spoony.spoony.presentation.explore.type.SortingOption
import com.spoony.spoony.presentation.register.model.RegisterType
import com.spoony.spoony.presentation.report.ReportType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentMap
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

private const val LOAD_MORE_THRESHOLD = 3

@Composable
fun ExploreRoute(
    paddingValues: PaddingValues,
    navigateToExploreSearch: () -> Unit,
    navigateToPlaceDetail: (Int) -> Unit,
    navigateToRegister: () -> Unit,
    navigateToEditReview: (Int, RegisterType) -> Unit,
    navigateToReport: (reportTargetId: Int, type: ReportType) -> Unit,
    viewModel: ExploreViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val lifecycleOwner = LocalLifecycleOwner.current
    val showSnackBar = LocalSnackBarTrigger.current
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle).collect { effect ->
            when (effect) {
                is ExploreSideEffect.ShowSnackbar -> {
                    showSnackBar(effect.message)
                }
                is ExploreSideEffect.ScrollToTop -> {
                    coroutineScope.launch {
                        listState.scrollToItem(0)
                    }
                }
            }
        }
    }

    val lifecycle = lifecycleOwner.lifecycle
    LaunchedEffect(lifecycle) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.refresh()
        }
    }

    with(state) {
        ExploreScreen(
            paddingValues = paddingValues,
            onClickSearch = navigateToExploreSearch,
            onRegisterButtonClick = navigateToRegister,
            onPlaceDetailItemClick = navigateToPlaceDetail,
            onReportButtonClick = navigateToReport,
            onEditButtonClick = navigateToEditReview,
            onFilterApplyButtonClick = viewModel::applyExploreFilter,
            onLocalReviewButtonClick = viewModel::localReviewToggle,
            onSelectSortingOptionButtonClick = viewModel::updateSelectedSortingOption,
            onTabChange = viewModel::updateExploreType,
            onRefresh = viewModel::refreshExploreScreen,
            onLoadNextPage = viewModel::getPlaceReviewListFiltered,
            onReviewDeleteButtonClick = viewModel::deleteReview,
            selectedSortingOption = selectedSortingOption,
            chipItems = chipItems,
            placeReviewList = placeReviewList,
            propertyItems = exploreFilterItems.properties,
            regionItems = exploreFilterItems.regions,
            ageItems = exploreFilterItems.ages,
            categoryItems = exploreFilterItems.categories,
            propertySelectedState = filterSelectionState.properties,
            regionSelectedState = filterSelectionState.regions,
            ageSelectedState = filterSelectionState.ages,
            categorySelectedState = filterSelectionState.categories,
            listState = listState,
            exploreType = exploreType
        )
    }
}

@Composable
private fun ExploreScreen(
    paddingValues: PaddingValues,
    onClickSearch: () -> Unit,
    onRegisterButtonClick: () -> Unit,
    onPlaceDetailItemClick: (Int) -> Unit,
    onReportButtonClick: (reportTargetId: Int, type: ReportType) -> Unit,
    onEditButtonClick: (Int, RegisterType) -> Unit,
    onFilterApplyButtonClick: (PersistentMap<Int, Boolean>, PersistentMap<Int, Boolean>, PersistentMap<Int, Boolean>, PersistentMap<Int, Boolean>) -> Unit,
    onLocalReviewButtonClick: () -> Unit,
    onSelectSortingOptionButtonClick: (SortingOption) -> Unit,
    onTabChange: (ExploreType) -> Unit,
    onRefresh: () -> Unit,
    onLoadNextPage: () -> Unit,
    onReviewDeleteButtonClick: (Int) -> Unit,
    selectedSortingOption: SortingOption,
    chipItems: ImmutableList<FilterOption>,
    placeReviewList: UiState<ImmutableList<PlaceReviewModel>>,
    propertyItems: ImmutableList<ExploreFilter>,
    regionItems: ImmutableList<ExploreFilter>,
    ageItems: ImmutableList<ExploreFilter>,
    categoryItems: ImmutableList<ExploreFilter>,
    propertySelectedState: PersistentMap<Int, Boolean>,
    regionSelectedState: PersistentMap<Int, Boolean>,
    ageSelectedState: PersistentMap<Int, Boolean>,
    categorySelectedState: PersistentMap<Int, Boolean>,
    listState: LazyListState,
    exploreType: ExploreType
) {
    val tabList = persistentListOf("전체", "팔로잉")
    var isSortingBottomSheetVisible by remember { mutableStateOf(false) }
    var isFilterBottomSheetVisible by remember { mutableStateOf(false) }
    var exploreFilterBottomSheetTabIndex by remember { mutableIntStateOf(0) }
    if (isSortingBottomSheetVisible) {
        ExploreSortingBottomSheet(
            onDismiss = { isSortingBottomSheetVisible = false },
            onClick = {
                onSelectSortingOptionButtonClick(it)
            },
            currentSortingOption = selectedSortingOption
        )
    }

    val propertyState = remember(isFilterBottomSheetVisible, propertySelectedState) {
        mutableStateMapOf<Int, Boolean>().apply {
            if (isFilterBottomSheetVisible) putAll(propertySelectedState)
        }
    }

    val categoryState = remember(isFilterBottomSheetVisible, categorySelectedState) {
        mutableStateMapOf<Int, Boolean>().apply {
            if (isFilterBottomSheetVisible) putAll(categorySelectedState)
        }
    }

    val regionState = remember(isFilterBottomSheetVisible, regionSelectedState) {
        mutableStateMapOf<Int, Boolean>().apply {
            if (isFilterBottomSheetVisible) putAll(regionSelectedState)
        }
    }

    val ageState = remember(isFilterBottomSheetVisible, ageSelectedState) {
        mutableStateMapOf<Int, Boolean>().apply {
            if (isFilterBottomSheetVisible) putAll(ageSelectedState)
        }
    }

    val filterStates = listOf(propertyState, categoryState, regionState, ageState)

    if (isFilterBottomSheetVisible) {
        ExploreFilterBottomSheet(
            onDismiss = {
                isFilterBottomSheetVisible = false
            },
            onFilterReset = {
                filterStates.forEach { it.clear() }
            },
            onSave = {
                isFilterBottomSheetVisible = false

                val (property, category, region, age) = filterStates.map { it.toPersistentMap() }

                onFilterApplyButtonClick(property, category, region, age)
            },
            onToggleFilter = { id, type ->
                val stateMap = when (type) {
                    FilterType.FILTER -> null
                    FilterType.LOCAL_REVIEW -> propertyState
                    FilterType.CATEGORY -> categoryState
                    FilterType.REGION -> regionState
                    FilterType.AGE -> ageState
                }

                stateMap?.let { it[id] = !(it[id] ?: false) }
            },
            propertyItems = propertyItems,
            regionItems = regionItems,
            ageItems = ageItems,
            categoryItems = categoryItems,
            propertySelectedState = propertyState,
            regionSelectedState = regionState,
            ageSelectedState = ageState,
            categorySelectedState = categoryState,
            tabIndex = exploreFilterBottomSheetTabIndex
        )
    }

    Column(
        modifier = Modifier
            .padding(paddingValues)
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
                onTabChange = onTabChange,
                tabList = tabList,
                exploreType = exploreType
            )
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_search_20),
                modifier = Modifier
                    .size(20.dp)
                    .noRippleClickable(onClickSearch),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        if (exploreType == ExploreType.ALL) {
            FilterChipRow(
                chipItems,
                onFilterClick = { filterType ->
                    handleFilterClick(
                        filterType = filterType,
                        onLocalReviewButtonClick = {
                            onLocalReviewButtonClick()
                        },
                        updateBottomSheetState = { index, isVisible ->
                            exploreFilterBottomSheetTabIndex = index
                            isFilterBottomSheetVisible = isVisible
                        }
                    )
                },
                onSortFilterClick = {
                    isSortingBottomSheetVisible = true
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
        ExploreContent(
            modifier = Modifier
                .padding(horizontal = 20.dp),
            placeReviewList = placeReviewList,
            onRegisterButtonClick = onRegisterButtonClick,
            onPlaceDetailItemClick = onPlaceDetailItemClick,
            onReportButtonClick = onReportButtonClick,
            onReviewDeleteButtonClick = onReviewDeleteButtonClick,
            onRefresh = onRefresh,
            onLoadNextPage = onLoadNextPage,
            onEditButtonClick = onEditButtonClick,
            onClickSearch = onClickSearch,
            exploreType = exploreType,
            listState = listState
        )
    }
}

private fun handleFilterClick(
    filterType: FilterType,
    onLocalReviewButtonClick: () -> Unit,
    updateBottomSheetState: (Int, Boolean) -> Unit
) {
    when (filterType) {
        FilterType.FILTER -> updateBottomSheetState(0, true)
        FilterType.LOCAL_REVIEW -> onLocalReviewButtonClick()
        FilterType.CATEGORY -> updateBottomSheetState(1, true)
        FilterType.REGION -> updateBottomSheetState(2, true)
        FilterType.AGE -> updateBottomSheetState(3, true)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExploreContent(
    onRegisterButtonClick: () -> Unit,
    onReportButtonClick: (reportTargetId: Int, type: ReportType) -> Unit,
    onPlaceDetailItemClick: (Int) -> Unit,
    onReviewDeleteButtonClick: (Int) -> Unit,
    onRefresh: () -> Unit,
    onLoadNextPage: () -> Unit,
    placeReviewList: UiState<ImmutableList<PlaceReviewModel>>,
    onEditButtonClick: (Int, RegisterType) -> Unit,
    onClickSearch: () -> Unit,
    exploreType: ExploreType,
    listState: LazyListState,
    modifier: Modifier = Modifier
) {
    var isLoadingMore by remember { mutableStateOf(false) }
    var isReviewDeleteDialogVisible by remember { mutableStateOf(false) }
    var targetReviewId by remember { mutableIntStateOf(0) }
    if (isReviewDeleteDialogVisible) {
        TwoButtonDialog(
            message = "정말로 리뷰를 삭제할까요?",
            negativeText = "아니요",
            positiveText = "네",
            onClickNegative = { isReviewDeleteDialogVisible = false },
            onClickPositive = {
                onReviewDeleteButtonClick(targetReviewId)
                isReviewDeleteDialogVisible = false
            },
            onDismiss = { }
        )
    }
    LaunchedEffect(listState, exploreType) {
        snapshotFlow {
            val layoutInfo = listState.layoutInfo
            val totalItems = layoutInfo.totalItemsCount
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleItem to totalItems
        }
            .distinctUntilChanged()
            .collect { (lastVisibleItem, totalItems) ->
                if (exploreType == ExploreType.ALL) {
                    if (
                        !isLoadingMore &&
                        lastVisibleItem >= totalItems - LOAD_MORE_THRESHOLD &&
                        totalItems > 0
                    ) {
                        isLoadingMore = true
                        onLoadNextPage()
                        isLoadingMore = false
                    }
                }
            }
    }

    when (placeReviewList) {
        is UiState.Empty -> {
            ExploreEmptyScreen(
                onClick = if (exploreType == ExploreType.ALL) onRegisterButtonClick else onClickSearch,
                exploreType = exploreType,
                modifier = Modifier
                    .fillMaxSize()
            )
        }

        is UiState.Success -> {
            val refreshState = rememberPullToRefreshState()

            val alpha by remember {
                derivedStateOf {
                    if (refreshState.isRefreshing) {
                        1f
                    } else {
                        refreshState.progress.let { it * it * it }
                    }
                }
            }

            LaunchedEffect(refreshState.isRefreshing) {
                if (refreshState.isRefreshing) {
                    onRefresh()
                    refreshState.endRefresh()
                }
            }
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .nestedScroll(refreshState.nestedScrollConnection)
            ) {
                LazyColumn(
                    state = listState,
                    contentPadding = PaddingValues(bottom = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = placeReviewList.data
                    ) { placeReview ->
                        val menuList = remember {
                            if (placeReview.isMine) {
                                persistentListOf(
                                    ExploreDropdownOption.EDIT.string,
                                    ExploreDropdownOption.DELETE.string
                                )
                            } else {
                                persistentListOf(
                                    ExploreDropdownOption.REPORT.string
                                )
                            }
                        }
                        ReviewCard(
                            reviewId = placeReview.reviewId,
                            username = placeReview.userName,
                            userRegion = placeReview.userRegion,
                            review = placeReview.description,
                            addMapCount = placeReview.addMapCount,
                            date = placeReview.createdAt,
                            imageList = placeReview.photoUrlList,
                            category = ReviewCardCategory(
                                text = placeReview.category.text,
                                iconUrl = placeReview.category.iconUrl,
                                backgroundColor = placeReview.category.backgroundColor,
                                textColor = placeReview.category.textColor
                            ),
                            menuItems = menuList,
                            onClick = { onPlaceDetailItemClick(placeReview.reviewId) },
                            onMenuItemClick = { option ->
                                when (option) {
                                    ExploreDropdownOption.REPORT.string -> onReportButtonClick(placeReview.reviewId, ReportType.POST)
                                    ExploreDropdownOption.EDIT.string -> onEditButtonClick(placeReview.reviewId, RegisterType.EDIT)
                                    ExploreDropdownOption.DELETE.string -> {
                                        targetReviewId = placeReview.reviewId
                                        isReviewDeleteDialogVisible = true
                                    }
                                }
                            }
                        )
                    }
                }
                SpoonyPullToRefreshContainer(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .zIndex(1f),
                    state = refreshState,
                    containerColor = SpoonyAndroidTheme.colors.main500,
                    contentColor = SpoonyAndroidTheme.colors.white,
                    alpha = { alpha }
                )
            }
        }

        else -> {}
    }
}
