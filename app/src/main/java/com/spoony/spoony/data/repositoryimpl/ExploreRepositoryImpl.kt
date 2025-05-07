package com.spoony.spoony.data.repositoryimpl

import com.spoony.spoony.data.datasource.ExploreRemoteDataSource
import com.spoony.spoony.data.mapper.toDomain
import com.spoony.spoony.domain.entity.FeedEntity
import com.spoony.spoony.domain.repository.ExploreRepository
import javax.inject.Inject

class ExploreRepositoryImpl @Inject constructor(
    private val exploreRemoteDataSource: ExploreRemoteDataSource
) : ExploreRepository {
    override suspend fun getFeedList(
        categoryId: Int,
        locationQuery: String,
        sortBy: String
    ): Result<List<FeedEntity>> = runCatching {
        exploreRemoteDataSource.getFeedList(
            categoryId = categoryId,
            query = locationQuery,
            sortBy = sortBy
        ).data!!.feedsResponseList.map {
            it.toDomain()
        }
    }
}
