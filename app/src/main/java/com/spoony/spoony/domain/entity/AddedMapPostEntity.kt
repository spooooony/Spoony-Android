package com.spoony.spoony.domain.entity

/**
 * mapper 생성 후 삭제 부탁합니다~
 */

data class AddedMapPostEntity(
    val placeId: Int,
    val placeName: String,
    val categoryEntity: CategoryEntity,
    val authorName: String,
    val authorRegionName: String,
    val postId: Int,
    val zzimCount: Int,
    val photoUrlList: List<String>
)
