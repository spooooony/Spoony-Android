package com.spoony.spoony.data.datasourceimpl

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.datasource.ReviewRemoteDataSource
import com.spoony.spoony.data.dto.response.UserPageReviewResponseDto
import com.spoony.spoony.data.service.ReviewService
import javax.inject.Inject

class ReviewRemoteDataSourceImpl @Inject constructor(
    private val reviewService: ReviewService
): ReviewRemoteDataSource {
    override suspend fun getMyReview(): BaseResponse<UserPageReviewResponseDto> =
        reviewService.getMyReview()

    override suspend fun getOtherReview(targetUserId: Int, isLocalReview: Boolean): BaseResponse<UserPageReviewResponseDto> =
        reviewService.getOtherReview(targetUserId, isLocalReview)

}
