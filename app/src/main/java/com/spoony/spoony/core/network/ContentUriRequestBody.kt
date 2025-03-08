package com.spoony.spoony.core.network

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.provider.MediaStore
import android.util.Size
import kotlinx.coroutines.Deferred
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import kotlin.math.min
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import timber.log.Timber
import java.util.WeakHashMap
import java.util.concurrent.ConcurrentHashMap
import kotlin.math.max

class ContentUriRequestBody @Inject constructor(
    context: Context,
    private val uri: Uri?,
    private val config: ImageConfig = ImageConfig.DEFAULT
) : RequestBody() {

    private val contentResolver = context.contentResolver
    private var compressedImage: ByteArray? = null
    private var metadata: ImageMetadata? = null

    private data class ImageMetadata(
        val fileName: String,
        val size: Long,
        val mimeType: String?
    ) {
        companion object {
            val EMPTY = ImageMetadata("", 0L, null)
        }
    }

    /**
     * 이미지 압축을 위한 설정값을 담는 데이터 클래스입니다.
     * @param maxWidth 리사이징 시 제한할 최대 너비
     * @param maxHeight 리사이징 시 제한할 최대 높이
     * @param maxFileSize 압축 후 목표하는 최대 파일 크기 (바이트)
     * @param initialQuality 압축 품질의 초기값 (0~100)
     * @param minQuality 압축 품질의 최소값 (0~100)
     * @param format 이미지 포맷 (기본은 JPEG)
     */
    data class ImageConfig(
        val maxWidth: Int,
        val maxHeight: Int,
        val maxFileSize: Int,
        val initialQuality: Int,
        val minQuality: Int,
        val format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG
    ) {
        companion object {
            val DEFAULT = ImageConfig(
                maxWidth = 1024,
                maxHeight = 1024,
                maxFileSize = 1024 * 1024,
                initialQuality = 100,
                minQuality = 20
            )
        }
    }

    init {
        uri?.let {
            metadata = extractMetadata(it)
        }
    }

    @Volatile
    private var prepareImageDeferred: Deferred<Result<Unit>>? = null

    /**
     * prepareImage(): 이미지 압축 작업을 비동기로 준비합니다.
     * 동일 인스턴스 내에서 여러 호출이 동시에 들어오면, 첫 번째 작업의 Deferred를 공유합니다.
     */
    suspend fun prepareImage(): Result<Unit> {
        if (compressedImage != null) return Result.success(Unit)

        prepareImageDeferred?.let { return it.await() }

        return coroutineScope {
            val deferred = async(Dispatchers.IO) {
                if (compressedImage == null && uri != null) {
                    compressImage(uri).onSuccess { bytes ->
                        compressedImage = bytes
                    }
                }
                Result.success(Unit)
            }
            prepareImageDeferred = deferred
            deferred.await()
        }
    }

    /**
     * toFormData(): OkHttp Multipart 전송을 위해 RequestBody를 생성합니다.
     * 메타데이터에서 파일명을 가져오며, 없으면 기본 이름("image.jpg")을 사용합니다.
     */
    fun toFormData(name: String): MultipartBody.Part =
        MultipartBody.Part.createFormData(
            name = name,
            filename = metadata?.fileName ?: DEFAULT_FILE_NAME,
            body = this
        )

    /**
     * compressImage(): 주어진 URI로부터 비트맵을 로드하고 압축 작업을 수행합니다.
     * 파일 크기가 1MB 이하이면 압축을 건너뛰고 100% 품질로 처리합니다.
     */
    private suspend fun compressImage(uri: Uri): Result<ByteArray> =
        withContext(Dispatchers.IO) {
            loadBitmap(uri).map { bitmap ->
                if ((metadata?.size ?: 0) <= config.maxFileSize) {
                    ByteArrayOutputStream().use { buffer ->
                        bitmap.compress(config.format, 100, buffer)
                        bitmap.recycle()
                        buffer.toByteArray()
                    }
                } else {
                    compressBitmap(bitmap).apply {
                        bitmap.recycle()
                    }
                }
            }
        }

    /**
     * loadBitmap(): ImageDecoder를 사용하여 URI에서 비트맵을 로드합니다.
     * 이미지 크기는 설정된 최대 크기(maxWidth, maxHeight) 이하로 리사이즈하며,
     * EXIF 정보는 ImageDecoder가 자동으로 처리합니다.
     */
    private suspend fun loadBitmap(uri: Uri): Result<Bitmap> =
        withContext(Dispatchers.IO) {
            runCatching {
                val source = ImageDecoder.createSource(contentResolver, uri)
                ImageDecoder.decodeBitmap(source) { decoder, info, _ ->
                    decoder.allocator = ImageDecoder.ALLOCATOR_SOFTWARE
                    decoder.isMutableRequired = true
                    val size = calculateTargetSize(info.size.width, info.size.height)
                    decoder.setTargetSize(size.width, size.height)
                }
            }
        }

    /**
     * compressBitmap(): CPU연산 부담이 큰 이미지 압축 작업은 Dispatchers.Default에서 처리합니다.
     * 이진 탐색을 통해 압축 품질을 결정하며, 메모리 기반 측정을 활용하여 압축 후 파일 크기를 측정합니다.
     * 임시파일 기반에서 바꾼 이유는 요즘 기기들 메모리 생각했을때 이게 맞는거 같음...속도도 빠르고...
     */
    private suspend fun compressBitmap(bitmap: Bitmap): ByteArray =
        withContext(Dispatchers.Default) {
            val estimatedSize = max(32 * 1024, min(bitmap.byteCount / 4, config.maxFileSize))
            ByteArrayOutputStream(estimatedSize).use { buffer ->
                var lowerQuality = config.minQuality
                var upperQuality = config.initialQuality
                var bestQuality = lowerQuality

                while (lowerQuality <= upperQuality) {
                    val midQuality = (lowerQuality + upperQuality) / 2
                    buffer.reset()

                    if (config.format == Bitmap.CompressFormat.PNG) {
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, buffer)
                    } else {
                        bitmap.compress(config.format, midQuality, buffer)
                    }

                    if (buffer.size() <= config.maxFileSize) {
                        bestQuality = midQuality
                        lowerQuality = midQuality + 1
                    } else {
                        upperQuality = midQuality - 1
                    }
                }

                Timber.d("Compression completed - Quality: $bestQuality, Size: ${buffer.size()} bytes")
                return@use buffer.toByteArray()
            }
        }

    private fun extractMetadata(uri: Uri): ImageMetadata =
        runCatching {
            contentResolver.query(
                uri,
                arrayOf(
                    MediaStore.Images.Media.SIZE,
                    MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media.MIME_TYPE
                ),
                null,
                null,
                null
            )?.use { cursor ->
                if (cursor.moveToFirst()) {
                    ImageMetadata(
                        fileName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)),
                        size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)),
                        mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE))
                    )
                } else {
                    ImageMetadata.EMPTY
                }
            } ?: ImageMetadata.EMPTY
        }.getOrDefault(ImageMetadata.EMPTY)

    /**
     * calculateTargetSize(): 이미지의 가로/세로 크기가
     * 설정된 최대 크기를 넘지 않도록 리사이징할 크기를 계산합니다.
     */
    private fun calculateTargetSize(width: Int, height: Int): Size {
        if (width <= config.maxWidth && height <= config.maxHeight) {
            return Size(width, height)
        }
        val scaleFactor = min(config.maxWidth / width.toFloat(), config.maxHeight / height.toFloat())
        val targetWidth = (width * scaleFactor).toInt()
        val targetHeight = (height * scaleFactor).toInt()
        return Size(targetWidth, targetHeight)
    }

    override fun contentLength(): Long = compressedImage?.size?.toLong() ?: -1L

    override fun contentType(): MediaType? = metadata?.mimeType?.toMediaTypeOrNull()

    /**
     * writeTo(): 압축된 이미지가 있을 경우, 해당 바이트 배열을 Sink에 씁니다.
     */
    override fun writeTo(sink: BufferedSink) {
        compressedImage?.let(sink::write)
    }

    companion object {
        const val DEFAULT_FILE_NAME = "image.jpg"

        /**
         * Uri를 키로 사용하여 ContentUriRequestBody 인스턴스를 저장하는 캐시입니다.
         * 이 캐시는 동일한 Uri에 대한 중복된 이미지 처리 작업을 방지합니다.
         * ConcurrentHashMap을 사용하여 멀티스레드 환경에서의 동시 접근을 안전하게 처리합니다.
         */
        private val cache = ConcurrentHashMap<Uri, ContentUriRequestBody>()

        fun getOrCreate(
            context: Context,
            uri: Uri,
            config: ImageConfig = ImageConfig.DEFAULT
        ): ContentUriRequestBody {
            return cache.computeIfAbsent(uri) {
                ContentUriRequestBody(context, uri, config)
            }
        }
    }
}
