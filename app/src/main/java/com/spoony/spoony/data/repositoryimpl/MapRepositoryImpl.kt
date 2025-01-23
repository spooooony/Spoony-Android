package com.spoony.spoony.data.repositoryimpl

import com.spoony.spoony.data.mapper.toDomain
import com.spoony.spoony.data.service.PostService
import com.spoony.spoony.domain.entity.AddedPlaceEntity
import com.spoony.spoony.domain.entity.CategoryEntity
import com.spoony.spoony.domain.entity.LocationEntity
import com.spoony.spoony.domain.repository.MapRepository
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(
    private val postService: PostService
) : MapRepository {
    override suspend fun searchLocation(query: String): Result<List<LocationEntity>> = Result.success(
        listOf(
            LocationEntity(
                locationId = 1,
                locationName = "강남구",
                locationAddress = "서울특별시 강남구"
            ),
            LocationEntity(
                locationId = 2,
                locationName = "종로구",
                locationAddress = "서울특별시 종로구"
            ),
            LocationEntity(
                locationId = 3,
                locationName = "중구",
                locationAddress = "서울특별시 중구"
            )
        )
    )

    override suspend fun getAddedPlaceList(userId: Int): Result<List<AddedPlaceEntity>> = Result.success(
        listOf(
            AddedPlaceEntity(
                placeId = 1,
                placeName = "BBQ",
                placeAddress = "서울특별시 마포구",
                postTitle = "안드들 낭만 챙겼다~~",
                photoUrl = "https://avatars.githubusercontent.com/u/160750136?v=4&size=40",
                latitude = 37.0,
                longitude = 127.0,
                categoryInfo = CategoryEntity(
                    categoryId = 3,
                    categoryName = "카페",
                    iconUrl = "https://avatars.githubusercontent.com/u/160750136?v=4&size=40",
                    textColor = "123456",
                    backgroundColor = "123456"
                )
            )
        )
    )

    override suspend fun getAddedPlaceListByLocation(userId: Int, locationId: Int): Result<List<AddedPlaceEntity>> =
        runCatching {
            postService.getZzimByLocation(userId, locationId)
                .data!!.zzimCardResponses.map { it.toDomain() }
        }
}
