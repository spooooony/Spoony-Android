package com.spoony.spoony.data.mapper

import com.spoony.spoony.data.dto.response.CategoryColorResponse
import com.spoony.spoony.data.dto.response.GetPostResponseDto
import com.spoony.spoony.domain.entity.CategoryEntity
import com.spoony.spoony.domain.entity.PlaceReviewEntity

fun GetPostResponseDto.toDomain() =
    PlaceReviewEntity(
        reviewId = this.postId,
        userId = this.userId,
        photoUrlList = this.photoUrlList,
        createdAt = this.date,
        menuList = this.menuList,
        description = this.description,
        value = this.value,
        cons = this.cons,
        placeName = this.placeName,
        placeAddress = this.placeAddress,
        latitude = this.latitude,
        longitude = this.longitude,
        addMapCount = this.zzimCount,
        isMine = this.isMine,
        isAddMap = this.isZzim,
        isScooped = this.isScoop,
        category = this.categoryColorResponse.toDomain()
    )

fun CategoryColorResponse.toDomain() =
    CategoryEntity(
        categoryId = this.categoryId,
        categoryName = this.categoryName,
        iconUrl = this.iconUrl,
        textColor = this.iconTextColor,
        backgroundColor = this.iconBackgroundColor
    )
