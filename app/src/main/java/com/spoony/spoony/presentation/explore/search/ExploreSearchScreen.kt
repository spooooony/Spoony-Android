package com.spoony.spoony.presentation.explore.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.core.util.extension.noRippleClickable
import com.spoony.spoony.presentation.explore.search.component.ExploreSearchEmptyScreen
import com.spoony.spoony.presentation.explore.search.component.ExploreSearchRecentEmptyScreen
import com.spoony.spoony.presentation.explore.search.component.ExploreSearchRecentItem
import com.spoony.spoony.presentation.explore.search.component.ExploreSearchTopAppbar
import com.spoony.spoony.presentation.explore.search.component.ExploreSearchUserItem
import com.spoony.spoony.presentation.explore.search.type.SearchType
import com.spoony.spoony.presentation.explore.search.type.toKoreanText
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@Composable
fun ExploreSearchRoute(
    paddingValues: PaddingValues,
    viewModel: ExploreSearchViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ExploreSearchScreen(
        paddingValues = paddingValues,
        searchKeyword = state.searchKeyword,
        searchType = state.searchType,
        onSwitchType = { searchType -> viewModel.switchType(searchType) },
        onRemoveRecentSearchItem = { keyword -> viewModel.removeRecentSearchItem(keyword) },
        onClearRecentSearchItem = viewModel::clearRecentSearchItem,
        onSearch = { keyword -> viewModel.search(keyword) },
        onClearSearchKeyword = viewModel::clearSearchKeyword,
        recentReviewSearchQueryList = state.recentReviewSearchQueryList,
        recentUserSearchQueryList = state.recentUserSearchQueryList,
        userInfoList = state.userInfoList,
        placeReviewInfoList = state.placeReviewInfoList
    )
}

@Composable
fun ExploreSearchScreen(
    paddingValues: PaddingValues,
    searchKeyword: String,
    searchType: SearchType,
    onRemoveRecentSearchItem: (String) -> Unit,
    onSwitchType: (SearchType) -> Unit,
    onClearRecentSearchItem: () -> Unit,
    onSearch: (String) -> Unit,
    onClearSearchKeyword: () -> Unit,
    recentReviewSearchQueryList: ImmutableList<String>,
    recentUserSearchQueryList: ImmutableList<String>,
    userInfoList: UiState<ImmutableList<UserInfo>>,
    placeReviewInfoList: UiState<ImmutableList<PlaceReviewInfo>>
) {
    val focusRequester = remember { FocusRequester() }
    var tabRowIndex by remember { mutableIntStateOf(0) }
    val tabItems = persistentListOf(SearchType.USER, SearchType.REVIEW)
    var searchText by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    LaunchedEffect(tabRowIndex) {
        searchText = ""
    }

    LaunchedEffect(searchText) {
        if (searchText.isEmpty()) {
            onClearSearchKeyword()
        }
    }

    Column {
        ExploreSearchTopAppbar(
            value = searchText,
            onValueChanged = {
                searchText = it
            },
            onBackButtonClick = {},
            onSearchAction = {
                searchText = searchText.trim()
                if (searchText.isNotEmpty()) onSearch(searchText)
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
                        tabRowIndex = index
                        onSwitchType(title)
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
                    searchKeyword.isBlank() -> {
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
                    else -> {
                        when (userInfoList) {
                            is UiState.Success -> {
                                LazyColumn(
                                    modifier = Modifier
                                        .padding(horizontal = 20.dp),
                                    contentPadding = PaddingValues(vertical = 30.dp)
                                ) {
                                    items(
                                        items = userInfoList.data
                                    ) { userInfo ->
                                        ExploreSearchUserItem(
                                            onItemClick = {},
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
                    searchKeyword.isBlank() -> {
                        when (recentReviewSearchQueryList.isEmpty()) {
                            true -> ExploreSearchRecentEmptyScreen(searchType = searchType)
                            else ->
                                ExploreSearchRecentContent(
                                    onRemoveRecentSearchItem = onRemoveRecentSearchItem,
                                    onClearRecentSearchItem = onClearRecentSearchItem,
                                    onItemClick = onSearch,
                                    recentQueryList = recentReviewSearchQueryList
                                )
                        }
                    }
                    else -> {
                        when (placeReviewInfoList) {
                            is UiState.Success -> {
                                // 탑색 뷰 컴포넌트 머지 후 구현 예정
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
                vertical = 22.dp
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
                modifier = Modifier.noRippleClickable(onClearRecentSearchItem)
            )
        }
        LazyColumn(
            modifier = Modifier
                .padding(vertical = 16.dp)
        ) {
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

@Preview(showBackground = true)
@Composable
private fun ExploreSearchScreenPreview() {
    val paddingValues = PaddingValues(0.dp)
    SpoonyAndroidTheme {
        var searchKeyword by remember { mutableStateOf("") }
        var recentUserQueryList by remember { mutableStateOf(persistentListOf<String>()) }
        var recentReviewQueryList by remember { mutableStateOf(persistentListOf<String>()) }
        var searchType by remember { mutableStateOf(SearchType.USER) }
        ExploreSearchScreen(
            paddingValues = paddingValues,
            searchKeyword = searchKeyword,
            searchType = searchType,
            onRemoveRecentSearchItem = { removedKeyword ->
                when (searchType) {
                    SearchType.USER ->
                        recentUserQueryList = recentUserQueryList
                            .filterNot { it == removedKeyword }
                            .toPersistentList()
                    SearchType.REVIEW ->
                        recentReviewQueryList = recentReviewQueryList
                            .filterNot { it == removedKeyword }
                            .toPersistentList()
                }
            },
            onSwitchType = {
                searchType = it
                searchKeyword = ""
            },
            onClearRecentSearchItem = {
                when (searchType) {
                    SearchType.USER -> recentUserQueryList = persistentListOf()
                    SearchType.REVIEW -> recentReviewQueryList = persistentListOf()
                }
            },
            onSearch = { keyword ->
                val keywordTrim = keyword.trim()
                when (searchType) {
                    SearchType.USER -> {
                        searchKeyword = keywordTrim
                        val updatedList = (listOf(keywordTrim) + recentUserQueryList.filterNot { it == keyword })
                            .take(6)
                            .toPersistentList()
                        recentUserQueryList = updatedList
                    }
                    SearchType.REVIEW -> {
                        searchKeyword = keywordTrim
                        val updatedList = (listOf(keywordTrim) + recentUserQueryList.filterNot { it == keyword })
                            .take(6)
                            .toPersistentList()
                        recentReviewQueryList = updatedList
                    }
                }
            },
            onClearSearchKeyword = {
                searchKeyword = ""
            },
            recentReviewSearchQueryList = recentReviewQueryList,
            recentUserSearchQueryList = recentUserQueryList,
            userInfoList = UiState.Success(
                persistentListOf(
                    UserInfo(
                        userId = 1,
                        nickname = "크리스탈에메랄드수정",
                        imageUrl = "https://github.com/user-attachments/assets/e25de1b2-a2df-465b-a4ff-c6ff8d85b5b4",
                        region = "서울 성북구"
                    ),
                    UserInfo(
                        userId = 2,
                        nickname = "크리스탈에메랄드수정",
                        imageUrl = "https://github.com/user-attachments/assets/e25de1b2-a2df-465b-a4ff-c6ff8d85b5b4",
                        region = "서울 성북구"
                    ),
                    UserInfo(
                        userId = 3,
                        nickname = "크리스탈에메랄드수정",
                        imageUrl = "https://github.com/user-attachments/assets/e25de1b2-a2df-465b-a4ff-c6ff8d85b5b4",
                        region = "서울 성북구"
                    )
                )
            ),
            placeReviewInfoList = UiState.Success(persistentListOf())
        )
    }
}
