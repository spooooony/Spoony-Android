package com.spoony.spoony.presentation.follow.model

import com.spoony.spoony.domain.entity.User

data class UserItemUiState(
    val userId: Int,
    val userName: String,
    val imageUrl: String,
    val region: String?,
    val isFollowing: Boolean
)

fun User.toModel(): UserItemUiState = UserItemUiState(
    userId = this.userId,
    userName = this.username,
    imageUrl = this.profileImageUrl,
    region = this.regionName,
    isFollowing = this.isFollowing
)
