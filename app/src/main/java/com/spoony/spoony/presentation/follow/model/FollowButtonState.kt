package com.spoony.spoony.presentation.follow.model

import androidx.compose.runtime.Immutable

@Immutable
enum class FollowButtonState {
    FOLLOWING,
    FOLLOW;

    fun toggle(): FollowButtonState {
        return if (this == FOLLOWING) FOLLOW else FOLLOWING
    }
}
