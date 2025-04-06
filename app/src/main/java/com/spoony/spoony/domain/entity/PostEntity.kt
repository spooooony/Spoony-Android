package com.spoony.spoony.domain.entity

/**
 * mapper 생성 후 삭제 부탁합니다~
 */

data class PostEntity(
    val postId: Int,
    val userId: Int,
    val photoUrlList: List<String>,
    val title: String,
    val date: String,
    val menuList: List<String>,
    val description: String,
    val placeName: String,
    val placeAddress: String,
    val latitude: Double,
    val longitude: Double,
    val addMapCount: Int,
    val isMine: Boolean,
    val isAddMap: Boolean,
    val isScooped: Boolean,
    val category: CategoryEntity
)
