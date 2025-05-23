package com.spoony.spoony.data.datasource

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.dto.response.UserPageReviewResponseDto

interface ReviewRemoteDataSource {
    suspend fun getMyReview():
        BaseResponse<UserPageReviewResponseDto>

    suspend fun getOtherReview(targetUserId: Int, isLocalReview: Boolean):
        BaseResponse<UserPageReviewResponseDto>
}
