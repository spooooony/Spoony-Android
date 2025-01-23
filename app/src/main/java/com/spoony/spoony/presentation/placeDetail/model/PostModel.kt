package com.spoony.spoony.presentation.placeDetail.model

import com.spoony.spoony.domain.entity.CategoryEntity
import com.spoony.spoony.domain.entity.PostEntity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class PostModel(
    val photoUrlList: ImmutableList<String>,
    val title: String,
    val date: String,
    val menuList: ImmutableList<String>,
    val description: String,
    val placeName: String,
    val placeAddress: String,
    val latitude: Double,
    val longitude: Double,
    val isMine: Boolean,
    val category: CategoryEntity
)

fun PostEntity.toModel(): PostModel = PostModel(
    photoUrlList = this.photoUrlList.toImmutableList(),
    title = this.title,
    date = this.date,
    menuList = this.menuList.toImmutableList(),
    description = this.description,
    placeName = this.placeName,
    placeAddress = this.placeAddress,
    latitude = this.latitude,
    longitude = this.longitude,
    isMine = this.isMine,
    category = this.category
)
