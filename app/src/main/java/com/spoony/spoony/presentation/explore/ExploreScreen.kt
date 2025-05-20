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
import com.spoony.spoony.presentation.explore.component.bottomsheet.ExploreSortingBottomSheet
import com.spoony.spoony.presentation.explore.model.FilterOption
import com.spoony.spoony.presentation.explore.model.PlaceReviewModel
import com.spoony.spoony.presentation.explore.type.SortingOption
import com.spoony.spoony.presentation.report.ReportType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ExploreRoute(
    paddingValues: PaddingValues,
    navigateToExploreSearch: () -> Unit,
    navigateToPlaceDetail: (Int) -> Unit,
    navigateToRegister: () -> Unit,
    navigateToReport: (reportTargetId: Int, type: ReportType) -> Unit,
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
            chipItems = state.chipItems,
            paddingValues = paddingValues,
            onClickSearch = navigateToExploreSearch,
            placeReviewList = placeReviewList,
            onRegisterButtonClick = navigateToRegister,
            onPlaceDetailItemClick = navigateToPlaceDetail,
            onReportButtonClick = navigateToReport
        )
    }
}

@Composable
private fun ExploreScreen(
    chipItems: ImmutableList<FilterOption>,
    paddingValues: PaddingValues,
    onClickSearch: () -> Unit,
    placeReviewList: UiState<ImmutableList<PlaceReviewModel>>,
    onRegisterButtonClick: () -> Unit,
    onPlaceDetailItemClick: (Int) -> Unit,
    onReportButtonClick: (reportTargetId: Int, type: ReportType) -> Unit
) {
    val tabList = persistentListOf("전체", "팔로잉")
    val selectedTabIndex = remember { mutableIntStateOf(0) }
    var isSortingBottomSheetVisible by remember { mutableStateOf(false) }
    var selectedSortingOption by remember { mutableStateOf(SortingOption.LATEST) }

    if (isSortingBottomSheetVisible) {
        ExploreSortingBottomSheet(
            onDismiss = { isSortingBottomSheetVisible = false },
            onClick = { selectedSortingOption = it },
            currentSortingOption = selectedSortingOption
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
    onReportButtonClick: (reportTargetId: Int, type: ReportType) -> Unit,
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
                                "신고하기" -> onReportButtonClick(placeReview.reviewId, ReportType.POST)
                            }
                        }
                    )
                }
            }
        }

        else -> {}
    }
}
