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
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.spoony.spoony.core.designsystem.component.topappbar.BackAndMenuTopAppBar
import com.spoony.spoony.core.designsystem.event.LocalSnackBarTrigger
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.theme.gray0
import com.spoony.spoony.core.designsystem.theme.white
import com.spoony.spoony.presentation.follow.component.FollowTabRow
import com.spoony.spoony.presentation.follow.component.PullToRefreshContainer
import com.spoony.spoony.presentation.follow.component.UserListScreen
import com.spoony.spoony.presentation.follow.model.FollowType
import com.spoony.spoony.presentation.follow.model.UserItemUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.launch

@Composable
fun FollowRoute(
    paddingValues: PaddingValues,
    onBackButtonClick: () -> Unit,
    navigateToUserProfile: (Int) -> Unit,
    navigateToMyPage: () -> Unit,
    viewModel: FollowViewModel = hiltViewModel()
) {
    val followers by viewModel.followers.collectAsStateWithLifecycle()
    val following by viewModel.following.collectAsStateWithLifecycle()
    val followType by viewModel.followType.collectAsStateWithLifecycle()

    val showSnackBar = LocalSnackBarTrigger.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle).collect { effect ->
            when (effect) {
                is FollowPageSideEffect.ShowSnackbar -> {
                    showSnackBar(effect.message)
                }
                is FollowPageSideEffect.ShowError -> {
                    showSnackBar(effect.errorType.description)
                }
            }
        }
    }

    FollowScreen(
        followers = followers,
        following = following,
        followType = followType,
        paddingValues = paddingValues,
        onUserClick = navigateToUserProfile,
        onMyClick = navigateToMyPage,
        onBackButtonClick = onBackButtonClick,
        onRefresh = viewModel::refresh,
        onFollowButtonClick = viewModel::toggleFollow
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
    onMyClick: () -> Unit,
    onBackButtonClick: () -> Unit,
    onRefresh: (FollowType) -> Unit,
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
        FollowType.fromPageIndex(pagerState.currentPage)
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
            .padding(bottom = paddingValues.calculateBottomPadding())
            .background(white)
    ) {
        BackAndMenuTopAppBar(
            onBackButtonClick = onBackButtonClick,
            modifier = Modifier.padding(horizontal = 20.dp),
            isMenuIconVisible = false
        )

        FollowTabRow(
            followerCount = followers.size,
            followingCount = following.size,
            onTabClick = { tabIndex ->
                coroutineScope.launch {
                    pagerState.animateScrollToPage(tabIndex)
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
                val type = FollowType.fromPageIndex(page)
                val users = remember(type, followers, following) {
                    when (type) {
                        FollowType.FOLLOWER -> followers
                        FollowType.FOLLOWING -> following
                    }
                }

                UserListScreen(
                    users = users,
                    onUserClick = onUserClick,
                    onMyClick = onMyClick,
                    onButtonClick = onFollowButtonClick
                )
            }

            PullToRefreshContainer(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .zIndex(1f),
                state = refreshState,
                containerColor = SpoonyAndroidTheme.colors.main500.copy(alpha = alpha),
                contentColor = SpoonyAndroidTheme.colors.white.copy(alpha = alpha)
            )
        }
    }
}
