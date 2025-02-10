package com.spoony.spoony.data.repositoryimpl

import android.content.Context
import android.net.Uri
import android.os.Debug
import androidx.core.graphics.component1
import androidx.core.graphics.component2
import com.spoony.spoony.core.network.ContentUriRequestBody
import com.spoony.spoony.core.network.ContentUriRequestBodyLegacy
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
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
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

        // 각 이미지에 대해 압축 성능 및 메모리 사용량 평가 수행
        val photoParts = photos.map { uri ->
            testCompressionPerformance(uri)
        }

        postService.registerPost(
            data = requestBody,
            photos = photoParts
        ).data
    }

    /**
     * 이미지 압축 성능 및 메모리 사용량 평가 함수
     *
     * - 원본 이미지 크기 (ContentResolver를 통해 측정)
     * - 압축 처리 전후의 네이티브 힙 메모리 사용량 측정 (Debug.getNativeHeapAllocatedSize)
     * - 압축 후 이미지 크기 (RequestBody의 contentLength())
     * - 실행 시간 측정
     *
     * 이를 통해 이미지 압축에 사용되는 메모리가 실제 원본 이미지 크기와 비교해 과도하게 할당되는지 확인하여 OOM 위험을 판단할 수 있습니다.
     */
    private suspend fun testCompressionPerformance(uri: Uri): MultipartBody.Part {
        val isTestMode = false // true이면 기존 방식, false이면 개선된 방식

        // 1. 원본 이미지 크기 측정 (바이트 단위)
        val originalSize: Long = context.contentResolver.openInputStream(uri)?.available()?.toLong() ?: -1L

        // 2. 압축 전 네이티브 힙 메모리 사용량 측정 (바이트 단위)
        val nativeHeapBefore: Long = getNativeHeapAllocatedSize()

        // 3. 이미지 압축 처리 및 실행 시간 측정
        lateinit var result: MultipartBody.Part
        val elapsedTime = measureTimeMillis {
            result = if (isTestMode) {
                // 기존 압축 방식 (예: ContentUriRequestBodyLegacy 사용)
                ContentUriRequestBodyLegacy(context, uri).toFormData("photos")
            } else {
                // 개선된 압축 방식 (예: ContentUriRequestBody 사용, prepareImage() 호출)
                ContentUriRequestBody(context, uri).apply {
                    prepareImage()
                }.toFormData("photos")
            }
        }

        // 4. 압축 후 네이티브 힙 메모리 사용량 측정 (바이트 단위)
        val nativeHeapAfter: Long = getNativeHeapAllocatedSize()

        // 5. 압축에 사용된 네이티브 힙 메모리 (바이트 단위)
        val memoryUsedForCompression: Long = nativeHeapAfter - nativeHeapBefore

        // 6. 압축 후 이미지 크기 측정 (RequestBody의 contentLength())
        val compressedSize: Long = result.body.contentLength()

        // 7. 로그 출력 (MB 단위로 변환)
        val originalSizeMB = originalSize / (1024f * 1024f)
        val compressedSizeMB = compressedSize / (1024f * 1024f)
        val memoryUsedMB = memoryUsedForCompression / (1024f * 1024f)

        Timber.d(
            """
            ✨ 이미지 압축 성능 및 메모리 사용량 평가 (${if (isTestMode) "기존" else "개선"} 방식):
            📊 원본 이미지 크기: $originalSizeMB MB
            📉 압축 후 이미지 크기: $compressedSizeMB MB
            📈 압축률: ${"%.2f".format((1 - (compressedSize.toFloat() / originalSize)) * 100)}%
            ⏱️ 실행 시간: ${elapsedTime}ms
            💾 압축에 사용된 네이티브 힙 메모리: $memoryUsedMB MB
            """.trimIndent()
        )

        // 원본 이미지 크기와 비교하여, 압축에 사용된 메모리가 과도하다면 경고 로그 출력 (OOM 위험 판단)
        if (memoryUsedForCompression > originalSize) {
            Timber.w("경고: 압축에 사용된 메모리($memoryUsedMB MB)가 원본 이미지 크기($originalSizeMB MB)보다 큽니다. OOM 위험이 있을 수 있습니다.")
        }

        return result
    }

    /**
     * Debug API를 사용하여 네이티브 힙에 할당된 메모리 크기를 측정합니다.
     * 반환 값은 바이트 단위입니다.
     */
    private fun getNativeHeapAllocatedSize(): Long {
        return Debug.getNativeHeapAllocatedSize()
    }
}
