package com.spoony.spoony.presentation.follow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.spoony.spoony.core.designsystem.component.topappbar.BackAndMenuTopAppBar
import com.spoony.spoony.core.designsystem.theme.gray0
import com.spoony.spoony.core.designsystem.theme.main400
import com.spoony.spoony.core.designsystem.theme.white
import com.spoony.spoony.presentation.follow.component.FollowTabRow
import com.spoony.spoony.presentation.follow.component.PullToRefreshContainer
import com.spoony.spoony.presentation.follow.model.UserItemUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FollowRoute(
    paddingValues: PaddingValues,
    navigateToUserProfile: (Int) -> Unit,
    onBackButtonClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: FollowViewModel = hiltViewModel()
) {
    val followers by viewModel.followers.collectAsState()
    val following by viewModel.following.collectAsState()
    val isFollowingTab by viewModel.isFollowingTab.collectAsState()

    FollowScreen(
        followers = followers,
        following = following,
        isFollowingTab = isFollowingTab,
        paddingValues = paddingValues,
        navigateToUserProfile = navigateToUserProfile,
        onBackButtonClick = onBackButtonClick,
        onRefreshFollowers = viewModel::refreshFollowers,
        onRefreshFollowings = viewModel::refreshFollowings,
        viewModel = viewModel,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FollowScreen(
    followers: ImmutableList<UserItemUiState>,
    following: ImmutableList<UserItemUiState>,
    isFollowingTab: Boolean,
    paddingValues: PaddingValues,
    navigateToUserProfile: (Int) -> Unit,
    onBackButtonClick: () -> Unit,
    onRefreshFollowers: suspend () -> Unit,
    onRefreshFollowings: suspend () -> Unit,
    viewModel: FollowViewModel,
    modifier: Modifier = Modifier
) {
    val initialPage = if (isFollowingTab) 1 else 0
    val pagerState = rememberPagerState(initialPage = initialPage) { 2 }
    val coroutineScope = rememberCoroutineScope()
    val refreshState = rememberPullToRefreshState()

    val alpha by remember {
        derivedStateOf {
            if (refreshState.isRefreshing) {
                1f
            } else {
                val progress = refreshState.progress
                progress * progress * progress
            }
        }
    }

    val counts by remember(followers.size, following.size) {
        derivedStateOf { Pair(followers.size, following.size) }
    }

    LaunchedEffect(refreshState.isRefreshing) {
        if (refreshState.isRefreshing) {
            if (pagerState.currentPage == 0) {
                onRefreshFollowers()
            } else {
                onRefreshFollowings()
            }
            refreshState.endRefresh()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(white)
    ) {
        BackAndMenuTopAppBar(
            onBackButtonClick = onBackButtonClick
        )

        FollowTabRow(
            followerCount = counts.first,
            followingCount = counts.second,
            onFollowerTabClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(0)
                }
            },
            onFollowingTabClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(1)
                }
            },
            selectedTabIndex = pagerState.currentPage
        )

        HorizontalDivider(
            thickness = 10.dp,
            color = gray0
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(refreshState.nestedScrollConnection)
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                when (page) {
                    0 -> FollowerRoute(
                        navigateToUserProfile = navigateToUserProfile,
                        viewModel = viewModel
                    )
                    1 -> FollowingRoute(
                        navigateToUserProfile = navigateToUserProfile,
                        viewModel = viewModel
                    )
                }
            }

            PullToRefreshContainer(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .zIndex(1f),
                state = refreshState,
                containerColor = main400.copy(alpha = alpha),
                contentColor = white.copy(alpha = alpha)
            )
        }
    }
}
