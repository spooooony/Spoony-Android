package com.spoony.spoony.data.mapper

import com.spoony.spoony.data.dto.response.GetPostResponseDTO
import com.spoony.spoony.domain.entity.CategoryEntity
import com.spoony.spoony.domain.entity.PostEntity
import kotlinx.collections.immutable.toImmutableList

fun GetPostResponseDTO.toDomain() =
    PostEntity(
        postId = this.postId,
        userId = this.userId,
        photoUrlList = this.photoUrlList.toImmutableList(),
        title = this.title,
        date = this.date,
        menuList = this.menuList.toImmutableList(),
        description = this.description,
        placeName = this.placeName,
        placeAddress = this.placeAddress,
        latitude = this.latitude,
        longitude = this.longitude,
        addMapCount = this.zzinCount,
        isAddMap = this.isZzim,
        isScooped = this.isScoop,
        category = this.categoryColorResponse.toDomain()
    )

fun GetPostResponseDTO.CategoryColorResponse.toDomain() =
    CategoryEntity(
        categoryId = this.categoryId,
        categoryName = this.categoryName,
        iconUrl = this.iconUrl,
        textColor = this.iconTextColor,
        backgroundColor = this.iconBackgroundColor
    )
