package com.spoony.spoony.presentation.placeDetail.model

import com.spoony.spoony.domain.entity.BasicUserInfoEntity

data class UserInfoModel(
    val userId: Int,
    val userName: String,
    val userProfileUrl: String,
    val userRegion: String
)

fun BasicUserInfoEntity.toModel(): UserInfoModel = UserInfoModel(
    userId = this.userId,
    userName = this.userName,
    userProfileUrl = this.profileImageUrl,
    userRegion = this.regionName
)
