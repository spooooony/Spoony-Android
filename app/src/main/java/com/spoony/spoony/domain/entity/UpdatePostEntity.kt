package com.spoony.spoony.domain.entity

data class UpdatePostEntity(
    val postId: Int,
    val description: String,
    val value: Float,
    val cons: String?,
    val categoryId: Int,
    val menuList: List<String>,
    val deleteImageUrls: List<String>,
    val newPhotos: List<String>
)
