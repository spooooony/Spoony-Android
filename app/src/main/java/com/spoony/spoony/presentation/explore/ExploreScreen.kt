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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.core.util.extension.hexToColor
import com.spoony.spoony.core.util.extension.toValidHexColor
import com.spoony.spoony.presentation.explore.component.ExploreEmptyScreen
import com.spoony.spoony.presentation.explore.component.ExploreItem
import com.spoony.spoony.presentation.explore.component.ExploreTabRow
import com.spoony.spoony.presentation.explore.component.FilterChipRow
import com.spoony.spoony.presentation.explore.model.FilterChip
import com.spoony.spoony.presentation.explore.model.FilterChipDataProvider
import com.spoony.spoony.presentation.explore.model.PlaceReviewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import okhttp3.internal.immutableListOf

@Composable
fun ExploreRoute(
    paddingValues: PaddingValues,
    navigateToPlaceDetail: (Int) -> Unit,
    navigateToRegister: () -> Unit,
    navigateToReport: (postId: Int, userId: Int) -> Unit,
    viewModel: ExploreViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val tabList = immutableListOf("전체", "팔로잉")
    val selectedTabIndex = remember { mutableIntStateOf(0) }
    val chipItems = FilterChipDataProvider.getDefaultFilterChips()

    with(state) {
        ExploreScreen2(
            tabList = tabList,
            chipItems = chipItems,
            selectedTabIndex = selectedTabIndex,
            paddingValues = paddingValues,
            placeReviewList = placeReviewList,
            onRegisterButtonClick = navigateToRegister,
            onPlaceDetailItemClick = navigateToPlaceDetail,
            onReportButtonClick = navigateToReport
        )
    }
}

@Composable
private fun ExploreScreen2(
    tabList: List<String>,
    chipItems: List<FilterChip>,
    selectedTabIndex: MutableState<Int>,
    paddingValues: PaddingValues,
    placeReviewList: UiState<ImmutableList<PlaceReviewModel>>,
    onRegisterButtonClick: () -> Unit,
    onPlaceDetailItemClick: (Int) -> Unit,
    onReportButtonClick: (postId: Int, userId: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding()
            )
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
    modifier: Modifier = Modifier,
    onRegisterButtonClick: () -> Unit,
    onReportButtonClick: (postId: Int, userId: Int) -> Unit,
    onPlaceDetailItemClick: (Int) -> Unit,
    placeReviewList: UiState<ImmutableList<PlaceReviewModel>>
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
                    ExploreItem(
                        username = placeReview.userName,
                        placeSpoon = placeReview.placeAddress,
                        review = placeReview.description,
                        addMapCount = placeReview.addMapCount,
                        date = placeReview.createdAt,
                        imageList = placeReview.photoUrlList,
                        backgroundColor = Color.hexToColor(placeReview.category?.backgroundColor.toValidHexColor()),
                        textColor = Color.hexToColor(placeReview.category?.textColor.toValidHexColor()),
                        tagText = placeReview.category?.categoryName,
                        iconUrl = placeReview.category?.iconUrl,
                        menuItems = menuItems,
                        onClick = { onPlaceDetailItemClick(placeReview.reviewId) },
                        onMenuItemClick = { option ->
                            when (option) {
                                "신고하기" -> onReportButtonClick(placeReview.reviewId, placeReview.userId)
                            }
                        },
                        modifier = Modifier
                    )
                }
            }
        }
        else -> {}
    }
}

// @Composable
// private fun ExploreScreen(
//    paddingValues: PaddingValues,
//    spoonCount: Int,
//    selectedCity: String,
//    selectedCategoryId: Int,
//    selectedSortingOption: SortingOption,
//    categoryList: ImmutableList<CategoryEntity>,
//    feedList: UiState<ImmutableList<FeedModel>>,
//    onLocationSortingButtonClick: (String) -> Unit,
//    onSortingButtonClick: (SortingOption) -> Unit,
//    onFeedItemClick: (Int) -> Unit,
//    onRegisterButtonClick: () -> Unit,
//    updateSelectedCategory: (Int) -> Unit
// ) {
//    var isLocationBottomSheetVisible by remember { mutableStateOf(false) }
//    var isSortingBottomSheetVisible by remember { mutableStateOf(false) }
//
//    if (isLocationBottomSheetVisible) {
//        ExploreLocationBottomSheet(
//            onDismiss = { isLocationBottomSheetVisible = false },
//            onClick = onLocationSortingButtonClick
//        )
//    }
//
//    if (isSortingBottomSheetVisible) {
//        ExploreSortingBottomSheet(
//            onDismiss = { isSortingBottomSheetVisible = false },
//            onClick = onSortingButtonClick,
//            currentSortingOption = selectedSortingOption
//        )
//    }
//
//    Column(
//        modifier = Modifier
//            .padding(bottom = paddingValues.calculateBottomPadding())
//    ) {
//        ExploreTopAppBar(
//            count = spoonCount,
//            onClick = {
//                isLocationBottomSheetVisible = true
//            },
//            place = selectedCity
//        )
//
//        LazyRow(
//            contentPadding = PaddingValues(horizontal = 20.dp),
//            horizontalArrangement = Arrangement.spacedBy(6.dp)
//        ) {
//            items(
//                items = categoryList,
//                key = { category ->
//                    category.categoryId
//                }
//            ) { category ->
//                IconChip(
//                    text = category.categoryName,
//                    unSelectedIconUrl = category.unSelectedIconUrl ?: "",
//                    selectedIconUrl = category.iconUrl,
//                    isSelected = selectedCategoryId == category.categoryId,
//                    isGradient = true,
//                    onClick = { updateSelectedCategory(category.categoryId) }
//                )
//            }
//        }
//
//        ExploreContent(
//            feedList = feedList,
//            selectedSortingOption = selectedSortingOption,
//            onSortingButtonClick = { isSortingBottomSheetVisible = it },
//            onFeedItemClick = onFeedItemClick,
//            onRegisterButtonClick = onRegisterButtonClick
//        )
//    }
// }
//
// @Composable
// private fun ExploreContent(
//    feedList: UiState<ImmutableList<FeedModel>>,
//    selectedSortingOption: SortingOption,
//    onSortingButtonClick: (Boolean) -> Unit,
//    onFeedItemClick: (Int) -> Unit,
//    onRegisterButtonClick: () -> Unit
// ) {
//    val menuItems = persistentListOf(ExploreDropdownOption.REPORT)
//    when (feedList) {
//        is UiState.Empty -> {
//            ExploreEmptyScreen(
//                onClick = onRegisterButtonClick,
//                modifier = Modifier
//                    .fillMaxSize()
//            )
//        }
//
//        is UiState.Success -> {
//            Column {
//                Box(
//                    contentAlignment = Alignment.CenterEnd,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .background(SpoonyAndroidTheme.colors.white)
//                        .padding(vertical = 16.dp, horizontal = 20.dp)
//                ) {
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        modifier = Modifier
//                            .noRippleClickable { onSortingButtonClick(true) }
//                            .padding(4.dp)
//                    ) {
//                        Text(
//                            text = selectedSortingOption.stringValue,
//                            style = SpoonyAndroidTheme.typography.caption1m,
//                            color = SpoonyAndroidTheme.colors.gray700,
//                            modifier = Modifier
//                                .padding(end = 2.dp)
//                        )
//
//                        Icon(
//                            imageVector = ImageVector.vectorResource(R.drawable.ic_filter_24),
//                            contentDescription = null,
//                            tint = SpoonyAndroidTheme.colors.gray700,
//                            modifier = Modifier
//                                .size(16.dp)
//                        )
//                    }
//                }
//
//                LazyColumn(
//                    contentPadding = PaddingValues(bottom = 12.dp),
//                    verticalArrangement = Arrangement.spacedBy(12.dp)
//                ) {
//                    items(
//                        items = feedList.data,
//                        key = { feed ->
//                            feed.feedId
//                        }
//                    ) { feed ->
//                        ExploreItem(
//                            username = feed.username,
//                            placeSpoon = feed.userRegion,
//                            review = feed.title,
//                            addMapCount = feed.addMapCount,
//                            iconUrl = feed.categoryEntity.iconUrl,
//                            tagText = feed.categoryEntity.categoryName,
//                            textColor = Color.hexToColor(feed.categoryEntity.textColor.toValidHexColor()),
//                            backgroundColor = Color.hexToColor(feed.categoryEntity.backgroundColor.toValidHexColor()),
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .noRippleClickable { onFeedItemClick(feed.feedId) }
//                                .padding(horizontal = 20.dp),
//                            date = "",
//                            menuItems = menuItems,
//                            onMenuItemClick = { option ->
//                                when (option) {
//                                    ExploreDropdownOption.REPORT -> {
//                                        // Handle report option
//                                    }
//                                }
//                            }
//                        )
//                    }
//                }
//            }
//        }
//
//        else -> {}
//    }
// }
