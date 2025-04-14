package com.spoony.spoony.presentation.follow

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.spoony.spoony.presentation.follow.component.UserListScreen
import com.spoony.spoony.presentation.follow.model.UserItemUiState
import kotlinx.collections.immutable.ImmutableList

@Composable
fun FollowerRoute(
    modifier: Modifier = Modifier,
    navigateToUserProfile: (Int) -> Unit,
    viewModel: FollowViewModel
) {
    val followers by viewModel.followers.collectAsState()
    val onFollowClick = remember(viewModel) { { userId: Int -> viewModel.toggleFollowForFollower(userId) } }

    FollowerRoute(
        followers = followers,
        onUserClick = navigateToUserProfile,
        onFollowClick = onFollowClick,
        modifier = modifier
    )
}

@Composable
private fun FollowerRoute(
    followers: ImmutableList<UserItemUiState>,
    onUserClick: (Int) -> Unit,
    onFollowClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        UserListScreen(
            users = followers,
            onUserClick = onUserClick,
            onFollowClick = onFollowClick,
            modifier = Modifier
        )
    }
}
