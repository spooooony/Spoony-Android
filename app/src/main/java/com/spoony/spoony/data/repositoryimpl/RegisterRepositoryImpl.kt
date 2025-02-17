package com.spoony.spoony.data.repositoryimpl

import android.content.Context
import android.net.Uri
import com.spoony.spoony.core.network.ContentUriRequestBody
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
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
        userId: Int,
        latitude: Double,
        longitude: Double
    ): Result<Boolean> = runCatching {
        placeDataSource.checkDuplicatePlace(userId, latitude, longitude).data!!.idDuplicated
    }

    override suspend fun registerPost(
        userId: Int,
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
    ): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
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

            coroutineScope {
                val requestBody = Json.encodeToString(RegisterPostRequestDto.serializer(), requestDto)
                    .toRequestBody("application/json".toMediaType())

                val photoParts = photos.map { uri ->
                    async {
                        ContentUriRequestBody(context, uri)
                            .apply { prepareImage() }
                            .toFormData("photos")
                    }
                }

                postService.registerPost(
                    data = requestBody,
                    photos = photoParts.awaitAll()
                ).data!!
            }
        }
    }
}
