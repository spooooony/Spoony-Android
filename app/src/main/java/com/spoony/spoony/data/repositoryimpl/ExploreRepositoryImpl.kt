package com.spoony.spoony.data.repositoryimpl

import com.spoony.spoony.core.database.ExploreRecentSearchDao
import com.spoony.spoony.core.database.entity.ExploreRecentSearchType
import com.spoony.spoony.data.datasource.ExploreRemoteDataSource
import com.spoony.spoony.data.mapper.toDomain
import com.spoony.spoony.domain.entity.ExplorePlaceReviewResultEntity
import com.spoony.spoony.domain.entity.PlaceReviewEntity
import com.spoony.spoony.domain.entity.UserEntity
import com.spoony.spoony.domain.repository.ExploreRepository
import javax.inject.Inject

class ExploreRepositoryImpl @Inject constructor(
    private val exploreRemoteDataSource: ExploreRemoteDataSource,
    private val exploreRecentSearchDao: ExploreRecentSearchDao
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

    override suspend fun getPlaceReviewListFiltered(categoryIds: List<Int>?, regionIds: List<Int>?, ageGroups: List<String>?, sortBy: String?, cursor: Int?, size: Int?): Result<ExplorePlaceReviewResultEntity> = runCatching {
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

        ExplorePlaceReviewResultEntity(
            reviews = reviewList,
            nextCursor = nextCursor
        )
    }

    override suspend fun getExploreRecentSearches(type: ExploreRecentSearchType): Result<List<String>> =
        runCatching {
            exploreRecentSearchDao.getQueriesExploreRecentSearch(type).map { it.keyword }
        }

    override suspend fun deleteExploreRecentSearch(type: ExploreRecentSearchType, searchText: String): Result<Unit> =
        runCatching {
            exploreRecentSearchDao.deleteExploreRecentSearch(type, searchText)
        }

    override suspend fun clearExploreRecentSearch(type: ExploreRecentSearchType): Result<Unit> =
        runCatching {
            exploreRecentSearchDao.clearExploreRecentSearch(type)
        }

    override suspend fun insertExploreRecentSearch(type: ExploreRecentSearchType, searchText: String) =
        runCatching {
            exploreRecentSearchDao.insertKeywordWithLimit(type, searchText)
        }
}
