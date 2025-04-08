package com.spoony.spoony.presentation.follow.model

data class UserItemUiState(
    val userId: Int,
    val userName: String,
    val imageUrl: String,
    val region: String,
    val followState: FollowButtonState
)
