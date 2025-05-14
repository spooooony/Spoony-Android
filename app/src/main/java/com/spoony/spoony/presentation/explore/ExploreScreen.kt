package com.spoony.spoony.presentation.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.card.ReviewCard
import com.spoony.spoony.core.designsystem.event.LocalSnackBarTrigger
import com.spoony.spoony.core.designsystem.model.ReviewCardCategory
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.core.util.extension.hexToColor
import com.spoony.spoony.core.util.extension.noRippleClickable
import com.spoony.spoony.core.util.extension.toValidHexColor
import com.spoony.spoony.presentation.explore.component.ExploreEmptyScreen
import com.spoony.spoony.presentation.explore.component.ExploreTabRow
import com.spoony.spoony.presentation.explore.component.FilterChipRow
import com.spoony.spoony.presentation.explore.component.bottomsheet.ExploreFilterBottomSheet
import com.spoony.spoony.presentation.explore.component.bottomsheet.ExploreSortingBottomSheet
import com.spoony.spoony.presentation.explore.model.ExploreFilter
import com.spoony.spoony.presentation.explore.model.FilterOption
import com.spoony.spoony.presentation.explore.model.FilterType
import com.spoony.spoony.presentation.explore.model.PlaceReviewModel
import com.spoony.spoony.presentation.explore.type.SortingOption
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ExploreRoute(
    paddingValues: PaddingValues,
    navigateToExploreSearch: () -> Unit,
    navigateToPlaceDetail: (Int) -> Unit,
    navigateToRegister: () -> Unit,
    navigateToReport: (postId: Int, userId: Int) -> Unit,
    viewModel: ExploreViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val lifecycleOwner = LocalLifecycleOwner.current
    val showSnackBar = LocalSnackBarTrigger.current

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle).collect { effect ->
            when (effect) {
                is ExploreSideEffect.ShowSnackbar -> {
                    showSnackBar(effect.message)
                }
            }
        }
    }

    with(state) {
        ExploreScreen(
            paddingValues = paddingValues,
            onClickSearch = navigateToExploreSearch,
            onRegisterButtonClick = navigateToRegister,
            onPlaceDetailItemClick = navigateToPlaceDetail,
            onReportButtonClick = navigateToReport,
            onFilterApplyButtonClick = viewModel::applyExploreFilter,
            onResetExploreFilterButtonClick = viewModel::resetExploreFilter,
            onLocalReviewButtonClick = viewModel::localReviewToggle,
            chipItems = chipItems,
            placeReviewList = placeReviewList,
            propertyItems = propertyItems,
            regionItems = regionItems,
            ageItems = ageItems,
            categoryItems = categoryItems,
            propertySelectedState = propertySelectedState,
            regionSelectedState = regionSelectedState,
            ageSelectedState = ageSelectedState,
            categorySelectedState = categorySelectedState
        )
    }
}

@Composable
private fun ExploreScreen(
    paddingValues: PaddingValues,
    onClickSearch: () -> Unit,
    onRegisterButtonClick: () -> Unit,
    onPlaceDetailItemClick: (Int) -> Unit,
    onReportButtonClick: (postId: Int, userId: Int) -> Unit,
    onFilterApplyButtonClick: (PersistentMap<Int, Boolean>, PersistentMap<Int, Boolean>, PersistentMap<Int, Boolean>, PersistentMap<Int, Boolean>) -> Unit,
    onResetExploreFilterButtonClick: () -> Unit,
    onLocalReviewButtonClick: () -> Unit,
    chipItems: ImmutableList<FilterOption>,
    placeReviewList: UiState<ImmutableList<PlaceReviewModel>>,
    propertyItems: ImmutableList<ExploreFilter>,
    regionItems: ImmutableList<ExploreFilter>,
    ageItems: ImmutableList<ExploreFilter>,
    categoryItems: ImmutableList<ExploreFilter>,
    propertySelectedState: PersistentMap<Int, Boolean>,
    regionSelectedState: PersistentMap<Int, Boolean>,
    ageSelectedState: PersistentMap<Int, Boolean>,
    categorySelectedState: PersistentMap<Int, Boolean>

) {
    val tabList = persistentListOf("전체", "팔로잉")
    val selectedTabIndex = remember { mutableIntStateOf(0) }
    var isSortingBottomSheetVisible by remember { mutableStateOf(false) }
    var isFilterBottomSheetVisible by remember { mutableStateOf(false) }
    var selectedSortingOption by remember { mutableStateOf(SortingOption.LATEST) }
    var exploreFilterBottomSheetTabIndex by remember { mutableIntStateOf(0) }

    if (isSortingBottomSheetVisible) {
        ExploreSortingBottomSheet(
            onDismiss = { isSortingBottomSheetVisible = false },
            onClick = { selectedSortingOption = it },
            currentSortingOption = selectedSortingOption
        )
    }
    val propertySelectedStateCopy = remember { mutableStateMapOf<Int, Boolean>().apply { putAll(propertySelectedState) } }
    val regionSelectedStateCopy = remember { mutableStateMapOf<Int, Boolean>().apply { putAll(regionSelectedState) } }
    val ageSelectedStateCopy = remember { mutableStateMapOf<Int, Boolean>().apply { putAll(ageSelectedState) } }
    val categorySelectedStateCopy = remember { mutableStateMapOf<Int, Boolean>().apply { putAll(categorySelectedState) } }

    if (isFilterBottomSheetVisible) {
        ExploreFilterBottomSheet(
            onDismiss = {
                isFilterBottomSheetVisible = false
            },
            onFilterReset = onResetExploreFilterButtonClick,
            onSave = onFilterApplyButtonClick,
            onToggleFilter = { id, type ->
                when (type) {
                    FilterType.LOCAL_REVIEW -> {
                        propertySelectedStateCopy[id] = !(propertySelectedStateCopy[id] ?: false)
                    }
                    FilterType.CATEGORY -> {
                        categorySelectedStateCopy[id] = !(categorySelectedStateCopy[id] ?: false)
                    }
                    FilterType.REGION -> {
                        regionSelectedStateCopy[id] = !(regionSelectedStateCopy[id] ?: false)
                    }
                    FilterType.AGE -> {
                        ageSelectedStateCopy[id] = !(ageSelectedStateCopy[id] ?: false)
                    }
                    else -> {}
                }
            },
            propertyItems = propertyItems,
            regionItems = regionItems,
            ageItems = ageItems,
            categoryItems = categoryItems,
            propertySelectedState = propertySelectedStateCopy,
            regionSelectedState = regionSelectedStateCopy,
            ageSelectedState = ageSelectedStateCopy,
            categorySelectedState = categorySelectedStateCopy,
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
                selectedTabIndex = selectedTabIndex,
                tabList = tabList
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
        FilterChipRow(
            chipItems,
            onFilterClick = { sort ->
                when (sort) {
                    FilterType.FILTER -> {
                        exploreFilterBottomSheetTabIndex = 0
                        isFilterBottomSheetVisible = true
                    }
                    FilterType.LOCAL_REVIEW -> {
                        onLocalReviewButtonClick()
                        propertySelectedStateCopy[1] = !(propertySelectedStateCopy[1] ?: false)
                    }
                    FilterType.CATEGORY -> {
                        exploreFilterBottomSheetTabIndex = 1
                        isFilterBottomSheetVisible = true
                    }
                    FilterType.REGION -> {
                        exploreFilterBottomSheetTabIndex = 2
                        isFilterBottomSheetVisible = true
                    }
                    FilterType.AGE -> {
                        exploreFilterBottomSheetTabIndex = 3
                        isFilterBottomSheetVisible = true
                    }
                }
            },
            onSortFilterClick = {
                isSortingBottomSheetVisible = true
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        ExploreContent(
            modifier = Modifier
                .padding(horizontal = 20.dp),
            placeReviewList = placeReviewList,
            onRegisterButtonClick = onRegisterButtonClick,
            onPlaceDetailItemClick = onPlaceDetailItemClick,
            onReportButtonClick = onReportButtonClick
        )
    }
}

@Composable
private fun ExploreContent(
    onRegisterButtonClick: () -> Unit,
    onReportButtonClick: (postId: Int, userId: Int) -> Unit,
    onPlaceDetailItemClick: (Int) -> Unit,
    placeReviewList: UiState<ImmutableList<PlaceReviewModel>>,
    modifier: Modifier = Modifier
) {
    val menuItems = persistentListOf(
        "신고하기"
    )
    when (placeReviewList) {
        is UiState.Empty -> {
            ExploreEmptyScreen(
                onClick = onRegisterButtonClick,
                modifier = Modifier
                    .fillMaxSize()
            )
        }

        is UiState.Success -> {
            LazyColumn(
                modifier = modifier,
                contentPadding = PaddingValues(bottom = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = placeReviewList.data,
                    key = { placeReview ->
                        placeReview.reviewId
                    }
                ) { placeReview ->
                    ReviewCard(
                        reviewId = placeReview.reviewId,
                        username = placeReview.userName,
                        userRegion = placeReview.userRegion,
                        review = placeReview.description,
                        addMapCount = placeReview.addMapCount,
                        date = placeReview.createdAt,
                        imageList = placeReview.photoUrlList,
                        category = ReviewCardCategory(
                            text = placeReview.category.categoryName,
                            iconUrl = placeReview.category.iconUrl,
                            backgroundColor = Color.hexToColor(placeReview.category.backgroundColor.toValidHexColor()),
                            textColor = Color.hexToColor(placeReview.category.textColor.toValidHexColor())
                        ),
                        menuItems = menuItems,
                        onClick = { onPlaceDetailItemClick(placeReview.reviewId) },
                        onMenuItemClick = { option ->
                            when (option) {
                                "신고하기" -> onReportButtonClick(placeReview.reviewId, placeReview.userId)
                            }
                        }
                    )
                }
            }
        }

        else -> {}
    }
}
