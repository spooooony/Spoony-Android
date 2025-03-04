package com.spoony.spoony.data.datasource

import com.spoony.spoony.data.dto.base.BaseResponse
import com.spoony.spoony.data.dto.response.FeedsResponseDto

interface ExploreRemoteDataSource {
    suspend fun getFeedList(
        categoryId: Int,
        query: String,
        sortBy: String
    ): BaseResponse<FeedsResponseDto>
}
