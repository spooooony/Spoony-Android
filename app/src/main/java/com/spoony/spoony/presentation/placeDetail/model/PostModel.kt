package com.spoony.spoony.presentation.placeDetail.model

import com.spoony.spoony.domain.entity.CategoryEntity
import com.spoony.spoony.domain.entity.PostEntity
import okhttp3.internal.toImmutableList

data class PostModel(
    val photoUrlList: List<String>,
    val title: String,
    val date: String,
    val menuList: List<String>,
    val description: String,
    val placeName: String,
    val placeAddress: String,
    val latitude: Double,
    val longitude: Double,
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
    category = this.category
)