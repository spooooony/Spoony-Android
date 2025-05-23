package com.spoony.spoony.domain.repository

import com.spoony.spoony.domain.entity.UserPageReviewEntity

interface ReviewRepository {
    suspend fun getMyReview(): Result<UserPageReviewEntity>

    suspend fun getOtherReview(targetUserId: Int, isLocalReview: Boolean):
            Result<UserPageReviewEntity>
}
