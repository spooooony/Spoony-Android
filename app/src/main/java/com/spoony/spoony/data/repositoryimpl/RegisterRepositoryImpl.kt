package com.spoony.spoony.data.repositoryimpl

import android.content.Context
import android.net.Uri
import com.spoony.spoony.data.datasource.CategoryDataSource
import com.spoony.spoony.data.datasource.PlaceDataSource
import com.spoony.spoony.data.dto.request.RegisterPostRequestDto
import com.spoony.spoony.data.mapper.toDomain
import com.spoony.spoony.data.service.PostService
import com.spoony.spoony.domain.entity.CategoryEntity
import com.spoony.spoony.domain.entity.PlaceEntity
import com.spoony.spoony.domain.repository.RegisterRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class RegisterRepositoryImpl @Inject constructor(
    private val placeDataSource: PlaceDataSource,
    private val categoryDataSource: CategoryDataSource,
    private val postService: PostService,
    @ApplicationContext private val context: Context
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
        placeDataSource.checkDuplicatePlace(userId, latitude, longitude).data!!.idDuplicated
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
        // 1. Request DTO를 RequestBody로 변환
        val requestDto = RegisterPostRequestDto(
            userId = userId,
            title = title,
            description = description,
            placeName = placeName,
            placeAddress = placeAddress,
            placeRoadAddress = placeRoadAddress,
            latitude = latitude,
            longitude = longitude,
            categoryId = categoryId,
            menuList = menuList
        )

        val jsonString = Json.encodeToString(RegisterPostRequestDto.serializer(), requestDto)
        val requestBody = jsonString.toRequestBody("application/json".toMediaType())

        // 2. ContentUriRequestBody를 사용하여 이미지 변환
        val photoParts = photos.map { uri ->
            ContentUriRequestBody(context, uri).toFormData("photos")
        }

        postService.registerPost(
            data = requestBody,
            photos = photoParts
        ).data
    }
}
