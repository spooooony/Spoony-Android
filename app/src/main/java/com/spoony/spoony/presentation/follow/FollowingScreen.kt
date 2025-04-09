package com.spoony.spoony.presentation.follow

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.spoony.spoony.presentation.follow.component.UserListScreen
import com.spoony.spoony.presentation.follow.model.UserItemUiState
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FollowingScreen(
    modifier: Modifier = Modifier,
    navigateToUserProfile: (Int) -> Unit,
    viewModel: FollowViewModel
) {
    val following by viewModel.following.collectAsState()

    val onFollowClick = remember(viewModel) { { userId: Int -> viewModel.toggleFollowForFollowing(userId) } }

    FollowingScreenContent(
        following = following,
        onUserClick = navigateToUserProfile,
        onFollowClick = onFollowClick,
        modifier = modifier
    )
}

@Composable
private fun FollowingScreenContent(
    following: ImmutableList<UserItemUiState>,
    onUserClick: (Int) -> Unit,
    onFollowClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        UserListScreen(
            users = following,
            onUserClick = onUserClick,
            onFollowClick = onFollowClick,
            modifier = Modifier
        )
    }
}
