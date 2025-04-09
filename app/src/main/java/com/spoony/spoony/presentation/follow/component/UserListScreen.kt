package com.spoony.spoony.presentation.follow.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spoony.spoony.presentation.follow.model.UserItemUiState
import kotlinx.collections.immutable.ImmutableList

@Composable
fun UserListScreen(
    users: ImmutableList<UserItemUiState>,
    onUserClick: (Int) -> Unit,
    onFollowClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
    ) {
        items(
            items = users,
            key = { it.userId }
        ) { user ->
            FollowUserItem(
                imageUrl = user.imageUrl,
                userName = user.userName,
                region = user.region,
                isFollowing = user.isFollowing,
                onUserClick = { onUserClick(user.userId) },
                onFollowClick = { onFollowClick(user.userId) },
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }
    }
}
