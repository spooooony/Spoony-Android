package com.spoony.spoony.data.mapper

import com.spoony.spoony.data.dto.response.FeedResponse
import com.spoony.spoony.data.dto.response.UserPageReviewResponseDto
import com.spoony.spoony.domain.entity.UserFeedEntity
import com.spoony.spoony.domain.entity.UserPageReviewEntity

fun UserPageReviewResponseDto.toDomain(): UserPageReviewEntity =
    UserPageReviewEntity(
        feedList = this.feedResponseList.map { it.toDomain() }
    )

fun FeedResponse.toDomain(): UserFeedEntity =
    UserFeedEntity(
        userId = this.userId,
        userName = this.userName,
        userRegion = this.userRegion,
        postId = this.postId,
        description = this.description,
        categoryInfo = this.categoryColorResponse.toDomain(),
        zzimCount = this.zzimCount,
        photoUrlList = this.photoUrlList,
        createdAt = this.createdAt,
        isMine = this.isMine
    )
