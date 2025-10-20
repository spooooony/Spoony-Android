package com.spoony.spoony.presentation.follow.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.analytics.events.LocalTracker
import com.spoony.spoony.presentation.follow.model.FollowType
import com.spoony.spoony.presentation.follow.model.UserItemUiState
import kotlinx.collections.immutable.ImmutableList

@Composable
fun UserListScreen(
    users: ImmutableList<UserItemUiState>,
    onUserClick: (Int) -> Unit,
    onMyClick: () -> Unit,
    onButtonClick: (Int) -> Unit,
    type: FollowType,
    modifier: Modifier = Modifier
) {
    val tracker = LocalTracker.current

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
                isMe = user.isMe,
                // TODO:region 관련된 처리 Manager 제작 by.민재
                region = if (user.region.isNullOrBlank()) "" else "서울 ${user.region} 스푼",
                isFollowing = user.isFollowing,
                onUserClick = { if (user.isMe) onMyClick() else onUserClick(user.userId) },
                onFollowClick = {
                    onButtonClick(user.userId)

                    when (type) {
                        FollowType.FOLLOWER -> {
                            if (user.isFollowing) {
                                tracker.commonEvents.unfollowUser(
                                    unfollowedUserId = user.userId,
                                    entryPoint = "followed_list"
                                )
                            } else {
                                tracker.commonEvents.followUser(
                                    followedUserId = user.userId,
                                    entryPoint = "followed_list"
                                )
                            }
                        }

                        FollowType.FOLLOWING -> {
                            if (user.isFollowing) {
                                tracker.commonEvents.unfollowUser(
                                    unfollowedUserId = user.userId,
                                    entryPoint = "following_list"
                                )
                            } else {
                                tracker.commonEvents.followUser(
                                    followedUserId = user.userId,
                                    entryPoint = "following_list"
                                )
                            }
                        }
                    }
                },
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }
    }
}
