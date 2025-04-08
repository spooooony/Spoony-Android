package com.spoony.spoony.presentation.follow

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.spoony.spoony.presentation.follow.component.UserListScreen

@Composable
fun FollowerScreen(
    modifier: Modifier = Modifier,
    navigateToUserProfile: (Int) -> Unit,
    viewModel: FollowViewModel
) {
    val followers by viewModel.followers.collectAsState()

    UserListScreen(
        users = followers,
        onUserClick = navigateToUserProfile,
        onFollowClick = { viewModel.toggleFollowForFollower(it) },
        modifier = modifier
    )
}
