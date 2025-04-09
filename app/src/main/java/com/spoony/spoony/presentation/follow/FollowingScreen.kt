package com.spoony.spoony.presentation.follow

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.spoony.spoony.presentation.follow.component.UserListScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FollowingScreen(
    modifier: Modifier = Modifier,
    navigateToUserProfile: (Int) -> Unit,
    viewModel: FollowViewModel
) {
    val following by viewModel.following.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        UserListScreen(
            users = following,
            onUserClick = navigateToUserProfile,
            onFollowClick = { viewModel.toggleFollowForFollowing(it) },
            modifier = Modifier
        )
    }
}
