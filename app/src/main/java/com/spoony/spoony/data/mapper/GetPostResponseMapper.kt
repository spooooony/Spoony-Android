package com.spoony.spoony.data.mapper

import com.spoony.spoony.data.dto.response.GetPostResponseDto
import com.spoony.spoony.domain.entity.CategoryEntity
import com.spoony.spoony.domain.entity.PostEntity

fun GetPostResponseDto.toDomain() =
    PostEntity(
        postId = this.postId,
        userId = this.userId,
        photoUrlList = this.photoUrlList,
        title = this.title,
        date = this.date,
        menuList = this.menuList,
        description = this.description,
        placeName = this.placeName,
        placeAddress = this.placeAddress,
        latitude = this.latitude,
        longitude = this.longitude,
        addMapCount = this.zzimCount,
        isAddMap = this.isZzim,
        isScooped = this.isScoop,
        category = this.categoryColorResponse.toDomain()
    )

fun GetPostResponseDto.CategoryColorResponse.toDomain() =
    CategoryEntity(
        categoryId = this.categoryId,
        categoryName = this.categoryName,
        iconUrl = this.iconUrl,
        textColor = this.iconTextColor,
        backgroundColor = this.iconBackgroundColor
    )
