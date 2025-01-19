package com.spoony.spoony.domain.entity

import kotlinx.collections.immutable.ImmutableList

data class PostEntity(
    val postId: Int,
    val userId: Int,
    val photoUrlList: ImmutableList<String>,
    val title: String,
    val date: String,
    val menuList: ImmutableList<String>,
    val description: String,
    val placeName: String,
    val placeAddress: String,
    val latitude: Double,
    val longitude: Double,
    val zzinCount: Int,
    val isZzim: Boolean,
    val isScoop: Boolean,
    val categoryColorResponse: IconTagEntity
)
