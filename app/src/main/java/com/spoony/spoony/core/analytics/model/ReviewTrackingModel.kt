package com.spoony.spoony.core.analytics.model

data class ReviewTrackingModel(
    val reviewId: Int,
    val authorUserId: Int,
    val placeName: String,
    val category: String,
    val menuCount: Int,
    val satisfactionScore: Double,
    val reviewLength: Int,
    val photoCount: Int,
    val hasDisappointment: Boolean,
    val savedCount: Int
)
