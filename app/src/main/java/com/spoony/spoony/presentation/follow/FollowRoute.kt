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
import com.spoony.spoony.presentation.follow.component.UserListScreen
import com.spoony.spoony.presentation.follow.model.FollowType
import com.spoony.spoony.presentation.follow.model.UserItemUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.launch

private fun FollowType.toPageIndex(): Int = ordinal

private fun Int.toFollowType(): FollowType = 
    FollowType.entries[Math.floorMod(this, FollowType.entries.size)]

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FollowRoute(
    paddingValues: PaddingValues,
    onBackButtonClick: () -> Unit,
    navigateToUserProfile: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FollowViewModel = hiltViewModel()
) {
    val followers by viewModel.followers.collectAsState()
    val following by viewModel.following.collectAsState()
    val followType by viewModel.followType.collectAsState()

    FollowScreen(
        followers = followers,
        following = following,
        followType = followType,
        paddingValues = paddingValues,
        onUserClick = navigateToUserProfile,
        onBackButtonClick = onBackButtonClick,
        onRefresh = { type ->
            when (type) {
                FollowType.FOLLOWER -> viewModel.refreshFollowers()
                FollowType.FOLLOWING -> viewModel.refreshFollowings()
            }
        },
        onFollowButtonClick = viewModel::toggleFollow,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FollowScreen(
    paddingValues: PaddingValues,
    followers: ImmutableList<UserItemUiState>,
    following: ImmutableList<UserItemUiState>,
    followType: FollowType,
    onUserClick: (Int) -> Unit,
    onBackButtonClick: () -> Unit,
    onRefresh: suspend (FollowType) -> Unit,
    onFollowButtonClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(initialPage = followType.toPageIndex()) { FollowType.entries.size }
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

    val currentType = remember(pagerState.currentPage) {
        pagerState.currentPage.toFollowType()
    }

    val counts = remember(followers.size, following.size) {
        Pair(followers.size, following.size)
    }

    LaunchedEffect(refreshState.isRefreshing) {
        if (refreshState.isRefreshing) {
            onRefresh(currentType)
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
                    pagerState.animateScrollToPage(FollowType.FOLLOWER.toPageIndex())
                }
            },
            onFollowingTabClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(FollowType.FOLLOWING.toPageIndex())
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
                val type = page.toFollowType()
                val users = remember(type, followers, following) {
                    when (type) {
                        FollowType.FOLLOWER -> followers
                        FollowType.FOLLOWING -> following
                    }
                }
                
                UserListScreen(
                    users = users,
                    onUserClick = onUserClick,
                    onButtonClick = onFollowButtonClick
                )
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
