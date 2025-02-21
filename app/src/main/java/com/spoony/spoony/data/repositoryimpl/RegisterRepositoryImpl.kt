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
import kotlin.system.measureTimeMillis
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import timber.log.Timber

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
    ): Result<Unit> = runCatching {
        coroutineScope {
            val totalTime = measureTimeMillis {
                val asyncRequestBody = async {
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
                    Json.encodeToString(RegisterPostRequestDto.serializer(), requestDto)
                        .toRequestBody(MEDIA_TYPE_JSON.toMediaType())
                }
                val asyncPhotoParts = photos.map { uri ->
                    async {
                        ContentUriRequestBody(context, uri)
                            .apply { prepareImage() }
                            .toFormData(FORM_DATA_NAME_PHOTOS)
                    }
                }
                postService.registerPost(
                    data = asyncRequestBody.await(),
                    photos = asyncPhotoParts.awaitAll()
                ).data
            }
            Timber.d("전체 업로드 소요 시간: $totalTime ms")
        }
    }

    companion object {
        private const val MEDIA_TYPE_JSON = "application/json"
        private const val FORM_DATA_NAME_PHOTOS = "photos"
    }
}
