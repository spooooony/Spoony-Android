package com.spoony.spoony.data.mapper

import com.spoony.spoony.data.dto.response.AddedMapPostDto
import com.spoony.spoony.domain.entity.AddedMapPostEntity

fun AddedMapPostDto.toDomain() = AddedMapPostEntity(
    placeId = this.placeId,
    placeName = this.placeName,
    categoryEntity = this.categoryColorResponse.toDomain(),
    authorName = this.authorName,
    authorRegionName = this.authorRegionName,
    postId = this.postId,
    postTitle = this.postTitle,
    zzimCount = this.zzimCount,
    photoUrlList = this.photoUrlList
)
