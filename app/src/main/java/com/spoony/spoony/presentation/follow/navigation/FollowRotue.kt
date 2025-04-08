package com.spoony.spoony.presentation.follow.navigation

import com.spoony.spoony.core.navigation.Route
import kotlinx.serialization.Serializable

@Serializable
sealed class FollowRoute : Route {
    @Serializable
    data object Follower : FollowRoute()

    @Serializable
    data object Following : FollowRoute()
}
