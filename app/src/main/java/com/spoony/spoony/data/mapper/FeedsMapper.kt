package com.spoony.spoony.data.mapper

import com.spoony.spoony.data.dto.response.FeedsResponseDto
import com.spoony.spoony.domain.entity.CategoryEntity
import com.spoony.spoony.domain.entity.FeedEntity

fun FeedsResponseDto.FeedsResponse.toDomain(): FeedEntity =
    FeedEntity(
        userId = this.userId,
        userName = this.userName,
        userRegion = this.userRegion,
        postId = this.postId,
        title = this.title,
        categoryInfo = this.categoryColorResponse.toDomain(),
        zzimCount = this.zzimCount
    )

fun FeedsResponseDto.FeedsResponse.CategoryColorResponse.toDomain(): CategoryEntity = CategoryEntity(
    categoryId = this.categoryId,
    categoryName = this.categoryName,
    iconUrl = this.iconUrl,
    textColor = this.iconTextColor,
    backgroundColor = this.iconBackgroundColor
)
