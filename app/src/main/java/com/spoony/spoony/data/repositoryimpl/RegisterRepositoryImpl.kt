package com.spoony.spoony.data.repositoryimpl

import android.content.Context
import com.spoony.spoony.core.network.ContentUriRequestBody
import com.spoony.spoony.data.datasource.CategoryDataSource
import com.spoony.spoony.data.datasource.PlaceDataSource
import com.spoony.spoony.data.dto.request.RegisterPostRequestDto
import com.spoony.spoony.data.dto.request.UpdatePostRequestDto
import com.spoony.spoony.data.mapper.toDomain
import com.spoony.spoony.data.service.PostService
import com.spoony.spoony.domain.entity.CategoryEntity
import com.spoony.spoony.domain.entity.PlaceEntity
import com.spoony.spoony.domain.entity.PlaceReviewEntity
import com.spoony.spoony.domain.entity.RegisterPostEntity
import com.spoony.spoony.domain.entity.UpdatePostEntity
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
import androidx.core.net.toUri

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
        latitude: Double,
        longitude: Double
    ): Result<Boolean> = runCatching {
        placeDataSource.checkDuplicatePlace(latitude, longitude).data!!.idDuplicated
    }

    override suspend fun registerPost(registerPostEntity: RegisterPostEntity): Result<Unit> = runCatching {
        coroutineScope {
            val totalTime = measureTimeMillis {
                val asyncRequestBody = async {
                    val requestDto = RegisterPostRequestDto(
                        description = registerPostEntity.description,
                        value = registerPostEntity.value,
                        cons = registerPostEntity.cons,
                        placeName = registerPostEntity.placeName,
                        placeAddress = registerPostEntity.placeAddress,
                        placeRoadAddress = registerPostEntity.placeRoadAddress,
                        latitude = registerPostEntity.latitude,
                        longitude = registerPostEntity.longitude,
                        categoryId = registerPostEntity.categoryId,
                        menuList = registerPostEntity.menuList
                    )
                    Json.encodeToString(RegisterPostRequestDto.serializer(), requestDto)
                        .toRequestBody(MEDIA_TYPE_JSON.toMediaType())
                }
                
                val asyncPhotoParts = registerPostEntity.photos.map { uriString ->
                    async {
                        val uri = uriString.toUri()
                        val photoBody = ContentUriRequestBody.getOrCreate(context, uri)
                        photoBody.prepareImage()
                        photoBody.toFormData(FORM_DATA_NAME_PHOTOS)
                    }
                }
                
                postService.registerPost(
                    data = asyncRequestBody.await(),
                    photos = asyncPhotoParts.awaitAll()
                ).data
            }
            Timber.d("게시물 등록 소요 시간: $totalTime ms")
        }
    }

    override suspend fun getPostDetail(postId: Int): Result<PlaceReviewEntity> = runCatching {
        postService.getPost(postId).data!!.toDomain()
    }

    override suspend fun updatePost(updatePostEntity: UpdatePostEntity): Result<Unit> = runCatching {
        coroutineScope {
            val totalTime = measureTimeMillis {
                val asyncRequestBody = async {
                    val requestDto = UpdatePostRequestDto(
                        postId = updatePostEntity.postId,
                        description = updatePostEntity.description,
                        value = updatePostEntity.value,
                        cons = updatePostEntity.cons,
                        categoryId = updatePostEntity.categoryId,
                        menuList = updatePostEntity.menuList,
                        deleteImageUrlList = updatePostEntity.deleteImageUrls
                    )
                    Json.encodeToString(UpdatePostRequestDto.serializer(), requestDto)
                        .toRequestBody(MEDIA_TYPE_JSON.toMediaType())
                }
                
                val asyncPhotoParts = updatePostEntity.newPhotos.map { uriString ->
                    async {
                        val uri = uriString.toUri()
                        val photoBody = ContentUriRequestBody.getOrCreate(context, uri)
                        photoBody.prepareImage()
                        photoBody.toFormData(FORM_DATA_NAME_PHOTOS)
                    }
                }
                
                postService.updatePost(
                    data = asyncRequestBody.await(),
                    photos = asyncPhotoParts.awaitAll()
                ).data
            }
            Timber.d("게시물 수정 소요 시간: $totalTime ms")
        }
    }

    companion object {
        private const val MEDIA_TYPE_JSON = "application/json"
        private const val FORM_DATA_NAME_PHOTOS = "photos"
    }
}
