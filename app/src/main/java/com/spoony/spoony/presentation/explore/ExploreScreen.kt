package com.spoony.spoony.presentation.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.spoony.spoony.core.designsystem.component.card.ReviewCard
import com.spoony.spoony.core.designsystem.component.pullToRefresh.SpoonyPullToRefreshContainer
import com.spoony.spoony.core.designsystem.event.LocalSnackBarTrigger
import com.spoony.spoony.core.designsystem.model.ReviewCardCategory
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.presentation.explore.component.ExploreEmptyScreen
import com.spoony.spoony.presentation.explore.component.ExploreFilterSection
import com.spoony.spoony.presentation.explore.component.ExploreHeaderSection
import com.spoony.spoony.presentation.explore.component.dialog.ReviewDeleteDialog
import com.spoony.spoony.presentation.explore.model.FilterOption
import com.spoony.spoony.presentation.explore.model.PlaceReviewModel
import com.spoony.spoony.presentation.explore.type.ExploreDropdownOption
import com.spoony.spoony.presentation.explore.type.SortingOption
import com.spoony.spoony.presentation.register.model.RegisterType
import com.spoony.spoony.presentation.report.ReportType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
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
                is ExploreSideEffect.NavigateToSearch -> navigateToExploreSearch()
                is ExploreSideEffect.NavigateToRegister -> navigateToRegister()
                is ExploreSideEffect.NavigateToPlaceDetail -> navigateToPlaceDetail(effect.id)
                is ExploreSideEffect.NavigateToEdit -> navigateToEditReview(effect.id, effect.type)
                is ExploreSideEffect.NavigateToReport -> navigateToReport(effect.targetId, effect.type)
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
            selectedSortingOption = selectedSortingOption,
            chipItems = chipItems,
            placeReviewList = placeReviewList,
            filterItems = exploreFilterItems,
            selectedFilterState = filterSelectionState,
            listState = listState,
            exploreType = exploreType,
            onAction = viewModel::onAction
        )
    }
}

@Composable
private fun ExploreScreen(
    paddingValues: PaddingValues,
    onAction: (ExploreAction) -> Unit,
    selectedSortingOption: SortingOption,
    chipItems: ImmutableList<FilterOption>,
    placeReviewList: UiState<ImmutableList<PlaceReviewModel>>,
    filterItems: ExploreFilterItems,
    selectedFilterState: ExploreFilterState,
    listState: LazyListState,
    exploreType: ExploreType
) {
    val tabList = persistentListOf("전체", "팔로잉")

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .background(SpoonyAndroidTheme.colors.white)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        ExploreHeaderSection(
            tabList = tabList,
            exploreType = exploreType,
            onChangeTab = { onAction(ExploreAction.ChangeTab(it)) },
            onClickSearch = { onAction(ExploreAction.Click.Search) }
        )

        Spacer(modifier = Modifier.height(24.dp))
        if (exploreType == ExploreType.ALL) {
            ExploreFilterSection(
                exploreType = exploreType,
                chipItems = chipItems,
                selectedSortingOption = selectedSortingOption,
                selectedFilterState = selectedFilterState,
                filterItems = filterItems,
                onAction = onAction
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
        ExploreContent(
            modifier = Modifier.padding(horizontal = 20.dp),
            placeReviewList = placeReviewList,
            exploreType = exploreType,
            listState = listState,
            onAction = onAction
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExploreContent(
    placeReviewList: UiState<ImmutableList<PlaceReviewModel>>,
    exploreType: ExploreType,
    listState: LazyListState,
    onAction: (ExploreAction) -> Unit,
    modifier: Modifier = Modifier
) {
    var isLoadingMore by remember { mutableStateOf(false) }
    var isReviewDeleteDialogVisible by remember { mutableStateOf(false) }
    var targetReviewId by remember { mutableIntStateOf(0) }

    ReviewDeleteDialog(
        isVisible = isReviewDeleteDialogVisible,
        onConfirm = { onAction(ExploreAction.DeleteReview(targetReviewId)) },
        onDismiss = { isReviewDeleteDialogVisible = false }
    )
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
                        onAction(ExploreAction.LoadNextPage)
                        isLoadingMore = false
                    }
                }
            }
    }

    when (placeReviewList) {
        is UiState.Empty -> {
            ExploreEmptyScreen(
                onClick = {
                    if (exploreType == ExploreType.ALL) {
                        onAction(ExploreAction.Click.Register)
                    } else {
                        onAction(ExploreAction.Click.Search)
                    }
                },
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
                    onAction(ExploreAction.Refresh)
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
                            onClick = { onAction(ExploreAction.Click.PlaceDetail(placeReview.reviewId)) },
                            onMenuItemClick = { option ->
                                when (option) {
                                    ExploreDropdownOption.REPORT.string -> onAction(ExploreAction.Click.Report(placeReview.reviewId, ReportType.POST))
                                    ExploreDropdownOption.EDIT.string -> onAction(ExploreAction.Click.Edit(placeReview.reviewId, RegisterType.EDIT))
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
