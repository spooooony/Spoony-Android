package com.spoony.spoony.presentation.placeDetail.model

import com.spoony.spoony.domain.entity.UserEntity

data class UserInfoModel(
    val userId: Int,
    val userName: String,
    val userProfileUrl: String,
    val userRegion: String
)

fun UserEntity.toModel(): UserInfoModel = UserInfoModel(
    userId = this.userId,
    userName = this.userName,
    userProfileUrl = this.userProfileUrl,
    userRegion = this.userRegion
)
