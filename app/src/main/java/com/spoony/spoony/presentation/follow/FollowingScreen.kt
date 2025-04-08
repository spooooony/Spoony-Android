package com.spoony.spoony.presentation.follow

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.spoony.spoony.presentation.follow.component.UserListScreen

@Composable
fun FollowingScreen(
    modifier: Modifier = Modifier,
    navigateToUserProfile: (Int) -> Unit,
    viewModel: FollowViewModel
) {
    val following by viewModel.following.collectAsState()

    UserListScreen(
        users = following,
        onUserClick = navigateToUserProfile,
        onFollowClick = { viewModel.toggleFollowForFollowing(it) },
        modifier = modifier
    )
}
