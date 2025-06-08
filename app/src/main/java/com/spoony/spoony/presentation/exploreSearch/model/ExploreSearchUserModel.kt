package com.spoony.spoony.presentation.exploreSearch.model

import com.spoony.spoony.domain.entity.UserEntity

data class ExploreSearchUserModel(
    val userId: Int,
    val userName: String,
    val userProfileUrl: String,
    val userRegion: String,
    val isMine: Boolean
)

fun UserEntity.toModel(): ExploreSearchUserModel = ExploreSearchUserModel(
    userId = this.userId,
    userName = this.userName,
    userProfileUrl = this.userProfileUrl,
    userRegion = this.userRegion ?: "",
    isMine = this.isMine ?: false
)
