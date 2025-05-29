package com.spoony.spoony.data.repositoryimpl

import com.spoony.spoony.data.datasource.ExploreRemoteDataSource
import com.spoony.spoony.data.mapper.toDomain
import com.spoony.spoony.domain.entity.PlaceReviewEntity
import com.spoony.spoony.domain.entity.UserEntity
import com.spoony.spoony.domain.repository.ExploreRepository
import javax.inject.Inject

class ExploreRepositoryImpl @Inject constructor(
    private val exploreRemoteDataSource: ExploreRemoteDataSource
) : ExploreRepository {
    override suspend fun getPlaceReviewByKeyword(query: String): Result<List<PlaceReviewEntity>> = runCatching {
        exploreRemoteDataSource.getPlaceReviewByKeyword(
            query = query
        ).data!!.postSearchResultList.map { it.toDomain() }
    }

    override suspend fun getUserListByKeyword(query: String): Result<List<UserEntity>> = runCatching {
        exploreRemoteDataSource.getUserListByKeyword(
            query = query
        ).data!!.userSimpleResponseDTO.map { it.toDomain() }
    }

    override suspend fun getPlaceReviewListFollowing(): Result<List<PlaceReviewEntity>> = runCatching {
        exploreRemoteDataSource.getPlaceReviewListFollowing().data!!.feedsResponseList.map { it.toDomain() }
    }

    override suspend fun getPlaceReviewListFiltered(categoryIds: List<Int>?, regionIds: List<Int>?, ageGroups: List<String>?, sortBy: String?, cursor: Int?, size: Int?): Result<Pair<List<PlaceReviewEntity>, Int?>> = runCatching {
        val data = exploreRemoteDataSource.getPlaceReviewListFiltered(
            categoryIds = categoryIds,
            regionIds = regionIds,
            ageGroups = ageGroups,
            sortBy = sortBy,
            cursor = cursor,
            size = size
        ).data!!

        val reviewList = data.filteredFeedResponseDTOList.map { it.toDomain() }
        val nextCursor = data.nextCursor

        reviewList to nextCursor
    }
}
