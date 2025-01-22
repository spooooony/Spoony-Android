package com.spoony.spoony.data.repositoryimpl

import android.net.Uri
import com.spoony.spoony.data.datasource.CategoryDataSource
import com.spoony.spoony.data.datasource.PlaceDataSource
import com.spoony.spoony.data.mapper.toDomain
import com.spoony.spoony.domain.entity.CategoryEntity
import com.spoony.spoony.domain.entity.PlaceEntity
import com.spoony.spoony.domain.repository.RegisterRepository
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val placeDataSource: PlaceDataSource,
    private val categoryDataSource: CategoryDataSource
) : RegisterRepository {
    override suspend fun getFoodCategories(): Result<List<CategoryEntity>> = runCatching {
        categoryDataSource.getFoodCategories().data!!.categoryMonoList.map { it.toDomain() }
    }

    override suspend fun searchPlace(query: String, display: Int): Result<List<PlaceEntity>> = runCatching {
        placeDataSource.getPlaces(query, display).data!!.placeList.map { it.toDomain() }
    }

    override suspend fun checkDuplicatePlace(
        userId: Long,
        latitude: Double,
        longitude: Double
    ): Result<Boolean> = runCatching {
        placeDataSource.checkDuplicatePlace(userId, latitude, longitude).data!!.duplicate
    }

    override suspend fun registerPost(
        userId: Long,
        title: String,
        description: String,
        placeName: String,
        placeAddress: String,
        placeRoadAddress: String,
        latitude: Double,
        longitude: Double,
        categoryId: Int,
        menuList: List<String>,
        photos: List<Uri>
    ): Result<Unit> = runCatching {
        // TODO: Multipart API 호출 구현
    }
}
