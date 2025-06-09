package com.spoony.spoony.presentation.setting.block.model

data class BlockUserState(
    val userId: Int,
    val userName: String,
    val imageUrl: String,
    val region: String?,
    val isBlocking: Boolean
)
