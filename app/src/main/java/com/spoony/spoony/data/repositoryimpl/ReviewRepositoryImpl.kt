package com.spoony.spoony.data.repositoryimpl

import com.spoony.spoony.data.datasource.ReviewRemoteDataSource
import com.spoony.spoony.data.mapper.toDomain
import com.spoony.spoony.domain.entity.UserPageReviewEntity
import com.spoony.spoony.domain.repository.ReviewRepository
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val reviewDataSource: ReviewRemoteDataSource
): ReviewRepository {
    override suspend fun getMyReview(): Result<UserPageReviewEntity> =
        runCatching {
            reviewDataSource.getMyReview().data!!.toDomain()
        }

    override suspend fun getOtherReview(targetUserId: Int, isLocalReview: Boolean): Result<UserPageReviewEntity> =
        runCatching {
            reviewDataSource.getOtherReview(targetUserId, isLocalReview).data!!.toDomain()
        }
}
