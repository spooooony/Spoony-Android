package com.spoony.spoony.presentation.follow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.spoony.spoony.core.designsystem.component.topappbar.BackAndMenuTopAppBar
import com.spoony.spoony.core.designsystem.theme.gray0
import com.spoony.spoony.core.designsystem.theme.white
import com.spoony.spoony.presentation.follow.component.FollowTabRow
import com.spoony.spoony.presentation.follow.navigation.FollowRoute
import com.spoony.spoony.presentation.follow.navigation.followGraph
import com.spoony.spoony.presentation.follow.navigation.navigateToFollowTab

@Composable
fun FollowMainScreen(
    paddingValues: PaddingValues,
    navigateToUserProfile: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FollowViewModel = hiltViewModel()
) {
    val followers by viewModel.followers.collectAsState()
    val following by viewModel.following.collectAsState()
    val isFollowingTab by viewModel.isFollowingTab.collectAsState()
    var selectedTab by remember { mutableIntStateOf(if (isFollowingTab) 1 else 0) }
    val navController = rememberNavController()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(white)
    ) {
        BackAndMenuTopAppBar(
            onBackButtonClick = {},
            onMenuButtonClick = {}
        )

        FollowTabRow(
            followerCount = followers.size,
            followingCount = following.size,
            onFollowerTabClick = {
                selectedTab = 0
                navController.navigateToFollowTab(FollowRoute.Follower)
            },
            onFollowingTabClick = {
                selectedTab = 1
                navController.navigateToFollowTab(FollowRoute.Following)
            },
            selectedTabIndex = selectedTab
        )

        HorizontalDivider(
            thickness = 10.dp,
            color = gray0
        )

        NavHost(
            navController = navController,
            startDestination = if (isFollowingTab) FollowRoute.Following else FollowRoute.Follower,
            modifier = Modifier.weight(1f)
        ) {
            followGraph(
                navigateToUserProfile = navigateToUserProfile,
                viewModel = viewModel
            )
        }
    }
}
