package com.spoony.spoony.presentation.setting.block

import android.R.attr.data
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.spoony.spoony.core.designsystem.component.screen.EmptyContent
import com.spoony.spoony.core.designsystem.component.topappbar.TitleTopAppBar
import com.spoony.spoony.core.designsystem.event.LocalSnackBarTrigger
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.theme.white
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.presentation.follow.component.PullToRefreshContainer
import com.spoony.spoony.presentation.setting.block.component.BlockUserItem
import com.spoony.spoony.presentation.setting.block.model.BlockUserState
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlockUserScreen(
    navigateUp: () -> Unit,
    viewModel: BlockUserViewModel = hiltViewModel()
) {
    val blockUserList by viewModel.blockingList.collectAsStateWithLifecycle()
    val showSnackBar = LocalSnackBarTrigger.current
    val lifecycleOwner = LocalLifecycleOwner.current
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

    LaunchedEffect(refreshState.isRefreshing) {
        if (refreshState.isRefreshing) {
            viewModel.getBlockingList()
            refreshState.endRefresh()
        }
    }

    LaunchedEffect(viewModel.errorEvent, lifecycleOwner) {
        viewModel.errorEvent.flowWithLifecycle(lifecycleOwner.lifecycle).collect { errorMessage ->
            showSnackBar(errorMessage)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(white)
    ) {
        TitleTopAppBar(
            title = "차단한 유저",
            onBackButtonClick = navigateUp
        )

        when (blockUserList) {
            is UiState.Loading -> {
                // TODO: Show loading indicator
            }
            is UiState.Empty -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    EmptyContent(
                        text = "차단한 유저가 없어요.",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            is UiState.Failure -> {
                // TODO: Show error state
            }
            is UiState.Success -> {
                HorizontalDivider(
                    thickness = 10.dp,
                    color = SpoonyAndroidTheme.colors.gray0
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(refreshState.nestedScrollConnection)
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        itemsIndexed(
                            items = (blockUserList as UiState.Success<ImmutableList<BlockUserState>>).data,
                            key = { _, item -> item.userId }
                        ) { index, user ->
                            BlockUserItem(
                                imageUrl = user.imageUrl,
                                userName = user.userName,
                                region = if (user.region.isNullOrBlank()) "" else "서울 ${user.region} 스푼",
                                isBlocking = user.isBlocking,
                                onBlockButtonClick = {
                                    viewModel.onClickBlockButton(user.userId, user.isBlocking)
                                },
                                modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp)
                            )
                            HorizontalDivider(
                                thickness = 2.dp,
                                color = SpoonyAndroidTheme.colors.gray0
                            )
                        }
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
    }
}

@Preview
@Composable
private fun BlockUserRoutePreview() {
    SpoonyAndroidTheme {
        BlockUserScreen(
            navigateUp = {}
        )
    }
}
