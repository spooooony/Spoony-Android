package com.spoony.spoony.data.datasourceimpl

import com.spoony.spoony.data.datasource.ExploreRemoteDataSource
import com.spoony.spoony.data.dto.base.BaseResponse
import com.spoony.spoony.data.dto.response.FeedsResponseDto
import com.spoony.spoony.data.service.ExploreService
import javax.inject.Inject

class ExploreRemoteDataSourceImpl @Inject constructor(
    private val exploreService: ExploreService
) : ExploreRemoteDataSource {
    override suspend fun getFeedList(
        userId: Int,
        categoryId: Int,
        query: String,
        sortBy: String
    ): BaseResponse<FeedsResponseDto> = exploreService.getFeedsList(
        userId = userId,
        categoryId = categoryId,
        query = query,
        sortBy = sortBy
    )
}
