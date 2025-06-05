package com.spoony.spoony.presentation.exploreSearch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.spoony.spoony.core.designsystem.component.card.ReviewCard
import com.spoony.spoony.core.designsystem.component.dialog.TwoButtonDialog
import com.spoony.spoony.core.designsystem.event.LocalSnackBarTrigger
import com.spoony.spoony.core.designsystem.model.ReviewCardCategory
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.core.util.extension.noRippleClickable
import com.spoony.spoony.presentation.exploreSearch.component.ExploreSearchEmptyScreen
import com.spoony.spoony.presentation.exploreSearch.component.ExploreSearchRecentEmptyScreen
import com.spoony.spoony.presentation.exploreSearch.component.ExploreSearchRecentItem
import com.spoony.spoony.presentation.exploreSearch.component.ExploreSearchTopAppbar
import com.spoony.spoony.presentation.exploreSearch.component.ExploreSearchUserItem
import com.spoony.spoony.presentation.exploreSearch.model.ExploreSearchPlaceReviewModel
import com.spoony.spoony.presentation.exploreSearch.model.ExploreSearchUserModel
import com.spoony.spoony.presentation.exploreSearch.type.ExploreDropdownOption
import com.spoony.spoony.presentation.exploreSearch.type.SearchType
import com.spoony.spoony.presentation.exploreSearch.type.toKoreanText
import com.spoony.spoony.presentation.register.model.RegisterType
import com.spoony.spoony.presentation.report.ReportType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ExploreSearchRoute(
    paddingValues: PaddingValues,
    navigateToUserProfile: (Int) -> Unit,
    navigateToReport: (reportTargetId: Int, type: ReportType) -> Unit,
    navigateToPlaceDetail: (Int) -> Unit,
    navigateToEditReview: (Int, RegisterType) -> Unit,
    navigateUp: () -> Unit,
    viewModel: ExploreSearchViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val showSnackBar = LocalSnackBarTrigger.current

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle).collect { effect ->
            when (effect) {
                is ExploreSearchSideEffect.ShowSnackBar -> {
                    showSnackBar(effect.message)
                }
            }
        }
    }

    ExploreSearchScreen(
        paddingValues = paddingValues,
        searchKeyword = state.searchKeyword,
        searchType = state.searchType,
        onReviewReportButtonClick = navigateToReport,
        onUserButtonClick = navigateToUserProfile,
        onPlaceDetailButtonClick = navigateToPlaceDetail,
        onBackButtonClick = navigateUp,
        onSwitchType = viewModel::switchSearchType,
        onRemoveRecentSearchItem = viewModel::removeRecentSearchItem,
        onClearRecentSearchItem = viewModel::clearRecentSearchItem,
        onSearch = viewModel::search,
        onEditReviewClick = navigateToEditReview,
        onClearSearchKeyword = viewModel::clearSearchKeyword,
        onReviewDeleteButtonClick = viewModel::deleteReview,
        recentReviewSearchQueryList = state.recentReviewSearchQueryList,
        recentUserSearchQueryList = state.recentUserSearchQueryList,
        userInfoList = state.userInfoList,
        placeReviewInfoList = state.placeReviewInfoList
    )
}

@Composable
private fun ExploreSearchScreen(
    paddingValues: PaddingValues,
    searchKeyword: String,
    searchType: SearchType,
    onReviewReportButtonClick: (reportTargetId: Int, type: ReportType) -> Unit,
    onUserButtonClick: (Int) -> Unit,
    onPlaceDetailButtonClick: (Int) -> Unit,
    onBackButtonClick: () -> Unit,
    onRemoveRecentSearchItem: (String) -> Unit,
    onSwitchType: (SearchType) -> Unit,
    onClearRecentSearchItem: () -> Unit,
    onSearch: (String) -> Unit,
    onEditReviewClick: (Int, RegisterType) -> Unit,
    onClearSearchKeyword: () -> Unit,
    onReviewDeleteButtonClick: (Int) -> Unit,
    recentReviewSearchQueryList: ImmutableList<String>,
    recentUserSearchQueryList: ImmutableList<String>,
    userInfoList: UiState<ImmutableList<ExploreSearchUserModel>>,
    placeReviewInfoList: UiState<ImmutableList<ExploreSearchPlaceReviewModel>>
) {
    val focusRequester = remember { FocusRequester() }
    var tabRowIndex by rememberSaveable { mutableIntStateOf(0) }
    val tabItems = persistentListOf(SearchType.USER, SearchType.REVIEW)
    var searchText by rememberSaveable { mutableStateOf("") }
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

    LaunchedEffect(Unit) {
        if (searchKeyword.isEmpty()) {
            focusRequester.requestFocus()
        }
    }

    LaunchedEffect(searchText) {
        if (searchText.isEmpty()) {
            onClearSearchKeyword()
        }
    }

    Column(
        modifier = Modifier
            .padding(
                bottom = paddingValues.calculateBottomPadding()
            )
    ) {
        ExploreSearchTopAppbar(
            value = searchText,
            onValueChanged = {
                searchText = it
            },
            onBackButtonClick = onBackButtonClick,
            onSearchAction = {
                onSearch(searchText)
            },
            focusRequester = focusRequester,
            searchType = searchType
        )
        TabRow(
            selectedTabIndex = tabRowIndex,
            containerColor = SpoonyAndroidTheme.colors.white,
            indicator = { tabPositions ->
                Box(
                    Modifier
                        .tabIndicatorOffset(tabPositions[tabRowIndex])
                        .height(2.dp)
                        .fillMaxWidth()
                        .background(SpoonyAndroidTheme.colors.main400)
                )
            },
            divider = { }
        ) {
            tabItems.forEachIndexed { index, title ->
                Tab(
                    selected = tabRowIndex == index,
                    modifier = Modifier
                        .padding(top = 4.dp, bottom = 9.dp),
                    onClick = {
                        if (tabRowIndex != index) {
                            tabRowIndex = index
                            onSwitchType(title)
                        }
                    },
                    selectedContentColor = SpoonyAndroidTheme.colors.white
                ) {
                    Text(
                        text = title.toKoreanText(),
                        style = SpoonyAndroidTheme.typography.body1sb,
                        color = if (tabRowIndex == index) SpoonyAndroidTheme.colors.main400 else SpoonyAndroidTheme.colors.gray400
                    )
                }
            }
        }
        HorizontalDivider(
            thickness = Dp.Hairline.plus(6.dp),
            color = SpoonyAndroidTheme.colors.gray100
        )
        when (tabRowIndex) {
            0 -> {
                when {
                    searchKeyword.isBlank() && searchText.isBlank() -> {
                        when (recentUserSearchQueryList.isEmpty()) {
                            true -> ExploreSearchRecentEmptyScreen(searchType = searchType)
                            else ->
                                ExploreSearchRecentContent(
                                    onRemoveRecentSearchItem = onRemoveRecentSearchItem,
                                    onClearRecentSearchItem = onClearRecentSearchItem,
                                    onItemClick = {
                                        searchText = it
                                        onSearch(searchText)
                                    },
                                    recentQueryList = recentUserSearchQueryList
                                )
                        }
                    }
                    searchKeyword.isBlank() && searchText.isNotBlank() -> {}
                    else -> {
                        when (userInfoList) {
                            is UiState.Success -> {
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 20.dp),
                                    contentPadding = PaddingValues(vertical = 30.dp)
                                ) {
                                    items(
                                        items = userInfoList.data
                                    ) { userInfo ->
                                        ExploreSearchUserItem(
                                            onItemClick = onUserButtonClick,
                                            userInfo = userInfo
                                        )
                                    }
                                }
                            }
                            is UiState.Empty -> {
                                ExploreSearchEmptyScreen(searchType = searchType)
                            }
                            else -> { }
                        }
                    }
                }
            }
            1 -> {
                when {
                    searchKeyword.isBlank() && searchText.isBlank() -> {
                        when (recentReviewSearchQueryList.isEmpty()) {
                            true -> ExploreSearchRecentEmptyScreen(searchType = searchType)
                            else ->
                                ExploreSearchRecentContent(
                                    onRemoveRecentSearchItem = onRemoveRecentSearchItem,
                                    onClearRecentSearchItem = onClearRecentSearchItem,
                                    onItemClick = {
                                        searchText = it
                                        onSearch(searchText)
                                    },
                                    recentQueryList = recentReviewSearchQueryList
                                )
                        }
                    }
                    searchKeyword.isBlank() && searchText.isNotBlank() -> {}
                    else -> {
                        when (placeReviewInfoList) {
                            is UiState.Success -> {
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            horizontal = 20.dp,
                                            vertical = 24.dp
                                        ),
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    items(
                                        items = placeReviewInfoList.data
                                    ) { placeReviewInfo ->
                                        val menuList = remember {
                                            if (placeReviewInfo.isMine) {
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
                                            reviewId = placeReviewInfo.reviewId,
                                            review = placeReviewInfo.description,
                                            onMenuItemClick = {
                                                when (it) {
                                                    ExploreDropdownOption.REPORT.string -> onReviewReportButtonClick(placeReviewInfo.reviewId, ReportType.POST)
                                                    ExploreDropdownOption.EDIT.string -> onEditReviewClick(placeReviewInfo.reviewId, RegisterType.EDIT)
                                                    ExploreDropdownOption.DELETE.string -> {
                                                        targetReviewId = placeReviewInfo.reviewId
                                                        isReviewDeleteDialogVisible = true
                                                    }
                                                }
                                            },
                                            date = placeReviewInfo.createdAt,
                                            username = placeReviewInfo.userName,
                                            userRegion = placeReviewInfo.userRegion,
                                            addMapCount = placeReviewInfo.addMapCount,
                                            imageList = placeReviewInfo.photoUrlList,
                                            menuItems = menuList,
                                            onClick = onPlaceDetailButtonClick,
                                            category = ReviewCardCategory(
                                                text = placeReviewInfo.category.text,
                                                iconUrl = placeReviewInfo.category.iconUrl,
                                                textColor = placeReviewInfo.category.textColor,
                                                backgroundColor = placeReviewInfo.category.backgroundColor
                                            )
                                        )
                                    }
                                }
                            }
                            is UiState.Empty -> {
                                ExploreSearchEmptyScreen(searchType = searchType)
                            }
                            else -> { }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ExploreSearchRecentContent(
    onRemoveRecentSearchItem: (String) -> Unit,
    onClearRecentSearchItem: () -> Unit,
    onItemClick: (String) -> Unit,
    recentQueryList: ImmutableList<String>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 20.dp,
                vertical = 33.dp
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "최근 검색",
                modifier = Modifier.weight(1f),
                style = SpoonyAndroidTheme.typography.body2b,
                color = SpoonyAndroidTheme.colors.gray700
            )
            Text(
                text = "전체삭제",
                style = SpoonyAndroidTheme.typography.caption1m,
                color = SpoonyAndroidTheme.colors.gray500,
                modifier = Modifier
                    .noRippleClickable(onClearRecentSearchItem)
                    .padding(horizontal = 8.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            itemsIndexed(
                items = recentQueryList
            ) { index, searchKeyword ->
                ExploreSearchRecentItem(
                    onItemClick = onItemClick,
                    onRemoveRecentSearchItem = onRemoveRecentSearchItem,
                    searchKeyword = searchKeyword
                )
                if (index < recentQueryList.lastIndex) {
                    HorizontalDivider(
                        color = SpoonyAndroidTheme.colors.gray0,
                        thickness = Dp.Hairline.plus(2.dp)
                    )
                }
            }
        }
    }
}
