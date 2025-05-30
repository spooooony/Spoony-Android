package com.spoony.spoony.data.mapper

import com.spoony.spoony.data.dto.response.CategoryColorDto
import com.spoony.spoony.data.dto.response.PlaceReviewResponseDto
import com.spoony.spoony.domain.entity.CategoryEntity
import com.spoony.spoony.domain.entity.PlaceReviewEntity

fun PlaceReviewResponseDto.toDomain(): PlaceReviewEntity =
    PlaceReviewEntity(
        reviewId = this.postId,
        userId = this.userId,
        userName = this.userName,
        userRegion = this.userRegion,
        description = this.description,
        category = this.categoryColorResponse.toDomain(),
        addMapCount = this.zzimCount,
        photoUrlList = this.photoUrlList,
        createdAt = this.createdAt,
        isMine = this.isMine
    )

fun CategoryColorDto.toDomain(): CategoryEntity = CategoryEntity(
    categoryId = this.categoryId,
    categoryName = this.categoryName,
    iconUrl = this.iconUrl,
    textColor = this.iconTextColor,
    backgroundColor = this.iconBackgroundColor
)
