package com.spoony.spoony.domain.entity

data class ExplorePlaceReviewResultEntity(
    val reviews: List<PlaceReviewEntity>,
    val nextCursor: String?
)
