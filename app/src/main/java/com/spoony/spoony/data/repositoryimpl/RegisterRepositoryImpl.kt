package com.spoony.spoony.data.repositoryimpl

import android.content.Context
import android.net.Uri
import android.os.Debug
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
import java.util.Locale
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
     * 측정 항목:
     * - 이미지 크기:
     *   - 원본 크기 (ContentResolver.available())
     *   - 압축 후 크기 (RequestBody.contentLength())
     *
     * - 메모리 사용량:
     *   - Java Heap: Runtime.totalMemory() - freeMemory()
     *   - Native Heap: Debug.getNativeHeapAllocatedSize()
     *   - GC 실행 후 측정하여 정확도 향상
     *
     * - 성능 지표:
     *   - 압축 소요 시간 (measureTimeMillis)
     *   - 압축률 (원본 대비 압축 후 크기)
     *
     * - OOM 위험도 평가:
     *   - 메모리 사용량/원본 크기 비율 기반
     *   - 낮음: 2배 미만
     *   - 중간: 2-3배
     *   - 높음: 3배 초과
     *
     * 결과는 Timber.d()를 통해 상세 로그로 출력됩니다.
     *
     * @param uri 압축할 이미지의 Uri
     * @return 압축된 이미지가 포함된 MultipartBody.Part
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

//    private suspend fun testCompressionPerformance(uri: Uri): MultipartBody.Part {
//        val isTestMode = false // true이면 기존 방식, false이면 개선된 방식
//
//        // 1. 원본 이미지 크기 측정
//        val originalSize = context.contentResolver.openInputStream(uri)?.use {
//            it.available().toLong()
//        } ?: -1L
//
//        val beforeMemory = measureMemoryState()
//
//        // 3. 이미지 압축 실행 및 시간 측정
//        lateinit var result: MultipartBody.Part
//        val compressionTime = measureTimeMillis {
//            result = if (isTestMode) {
//                ContentUriRequestBodyLegacy(context, uri).toFormData("photos")
//            } else {
//                ContentUriRequestBody(context, uri).apply {
//                    prepareImage()
//                }.toFormData("photos")
//            }
//        }
//
//        // 4. 압축 후 메모리 상태
//        val afterMemory = measureMemoryState()
//
//        // 5. 압축된 크기 확인
//        val compressedSize = result.body.contentLength()
//
//        // 메모리 사용량 계산
//        val memoryDiff = afterMemory - beforeMemory
//
//        Timber.d(
//            """
//        📸 이미지 압축 성능 분석 (${if (isTestMode) "기존" else "개선"} 방식):
//
//        📊 크기 정보:
//        - 원본: ${originalSize.bytesToMB()} MB
//        - 압축 후: ${compressedSize.bytesToMB()} MB
//        - 압축률: ${calculateCompressionRate(originalSize, compressedSize)}%
//
//        💾 메모리 사용량:
//        - 압축 전: ${beforeMemory.bytesToMB()} MB
//        - 압축 후: ${afterMemory.bytesToMB()} MB
//        - 실제 사용: ${maxOf(0L, memoryDiff).bytesToMB()} MB
//
//        ⚡ 성능:
//        - 처리 시간: ${compressionTime}ms
//        - 최대 가용 메모리: ${Runtime.getRuntime().maxMemory().bytesToMB()} MB
//
//        ⚠️ OOM 위험도: ${assessOOMRisk(originalSize, maxOf(0L, memoryDiff))}
//            """.trimIndent()
//        )
//
//        return result
//    }
//
//    private fun Long.bytesToMB() = this / (1024.0 * 1024.0)
//
//    private fun calculateCompressionRate(originalSize: Long, compressedSize: Long): String {
//        return String.format(Locale.US, "%.1f", (1 - compressedSize.toDouble() / originalSize) * 100)
//    }
//
//    private fun assessOOMRisk(originalSize: Long, usedMemory: Long): String {
//        val ratio = usedMemory.toDouble() / originalSize.toDouble()
//        return when {
//            ratio > 3.0 -> "높음 (메모리 사용량이 원본 대비 3배 초과)"
//            ratio > 2.0 -> "중간 (메모리 사용량이 원본 대비 2-3배)"
//            else -> "낮음 (메모리 사용량이 원본 대비 2배 미만)"
//        }
//    }
//
//    private fun measureMemoryState(): Long {
//        var attempt = 0
//        var memoryState: Long
//
//        // 최대 3번 시도하며 안정적인 메모리 측정값을 얻음
//        do {
//            System.gc()
//            Thread.sleep(200) // GC 완료를 위해 대기 시간 증가
//
//            val javaHeap = Runtime.getRuntime().run {
//                totalMemory() - freeMemory()
//            }
//            val nativeHeap = Debug.getNativeHeapAllocatedSize()
//            memoryState = javaHeap + nativeHeap
//
//            attempt++
//        } while (attempt < 3 && memoryState < 0)
//
//        return memoryState
//    }
}
