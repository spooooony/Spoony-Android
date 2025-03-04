package com.spoony.spoony.core.network

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.provider.MediaStore
import android.util.Size
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import kotlin.math.min
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import timber.log.Timber

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
        if (uri != null) {
            metadata = extractMetadata(uri)
        }
    }

    /**
     * prepareImage(): 이미지 압축 작업을 비동기로 준비합니다.
     * URI가 null이 아닌 경우 compressImage()를 호출하여 압축 결과를 저장합니다.
     */
    suspend fun prepareImage(): Result<Unit> = runCatching {
        withContext(Dispatchers.IO) {
            if (uri != null) {
                compressImage(uri).onSuccess { bytes ->
                    compressedImage = bytes
                }
            }
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
     * prepareImage()에서 호출되어 압축된 이미지를 반환합니다.
     */
    private suspend fun compressImage(uri: Uri): Result<ByteArray> =
        withContext(Dispatchers.IO) {
            loadBitmap(uri).map { bitmap ->
                compressBitmap(bitmap).also { bitmap.recycle() }
            }
        }

    /**
     * loadBitmap(): ImageDecoder를 사용하여 URI에서 비트맵을 로드합니다.
     * 이미지 크기는 설정된 최대 크기(maxWidth, maxHeight) 이하로 리사이즈하며,
     * EXIF 정보를 고려하여 회전이 필요한 경우 rotateBitmap()을 호출합니다.
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
            }.map { bitmap ->
                val orientation = getOrientation(uri)
                if (orientation != ORIENTATION_NORMAL) {
                    rotateBitmap(bitmap, orientation)
                } else {
                    bitmap
                }
            }
        }

    /**
     * compressBitmap(): 연산 부담이 큰 이미지 압축 작업은 Dispatchers.Default에서 처리합니다.
     * 이진 탐색을 통해 압축 품질을 결정하며, 임시 파일을 활용하여 압축 후 파일 크기를 측정합니다.
     */
    private suspend fun compressBitmap(bitmap: Bitmap): ByteArray =
        withContext(Dispatchers.Default) {
            var lowerQuality = config.minQuality
            var upperQuality = config.initialQuality
            var bestQuality = config.minQuality

            while (lowerQuality <= upperQuality) {
                val midQuality = (lowerQuality + upperQuality) / 2
                val currentSize = measureCompressedSize(bitmap, midQuality)
                if (currentSize <= config.maxFileSize) {
                    bestQuality = midQuality
                    lowerQuality = midQuality + 1
                } else {
                    upperQuality = midQuality - 1
                }
            }
            Timber.d("Compression completed - Quality: $bestQuality")
            compressToByteArray(bitmap, bestQuality)
        }

    /**
     * measureCompressedSize(): 임시 파일을 생성하여 압축 후 파일 크기를 측정합니다.
     * 이 방식은 메모리 기반 측정보다 대용량 이미지 처리 시 OutOfMemory 위험을 낮춥니다.
     */
    private fun measureCompressedSize(bitmap: Bitmap, quality: Int): Int {
        val tempFile = File.createTempFile("measure", ".tmp")
        FileOutputStream(tempFile).use { fos ->
            bitmap.compress(config.format, quality, fos)
        }
        return tempFile.length().toInt()
    }

    /**
     * compressToByteArray(): ByteArrayOutputStream을 사용하여 압축 결과를 ByteArray로 반환합니다.
     * PNG 포맷의 경우 품질 설정이 무시되므로 100으로 압축합니다.
     */
    private fun compressToByteArray(bitmap: Bitmap, quality: Int): ByteArray {
        return ByteArrayOutputStream().apply {
            if (config.format == Bitmap.CompressFormat.PNG) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, this)
            } else {
                bitmap.compress(config.format, quality, this)
            }
        }.toByteArray()
    }

    private fun extractMetadata(uri: Uri): ImageMetadata =
        runCatching {
            contentResolver.query(
                uri,
                arrayOf(
                    MediaStore.Images.Media.SIZE,
                    MediaStore.Images.Media.DISPLAY_NAME
                ),
                null,
                null,
                null
            )?.use { cursor ->
                if (cursor.moveToFirst()) {
                    ImageMetadata(
                        fileName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)),
                        size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)),
                        mimeType = contentResolver.getType(uri)
                    )
                } else {
                    ImageMetadata.EMPTY
                }
            } ?: ImageMetadata.EMPTY
        }.getOrDefault(ImageMetadata.EMPTY)

    /**
     * getOrientation(): MediaStore의 ORIENTATION 값을 우선적으로 조회하고, 없으면 EXIF 데이터를 사용하여 회전 각도를 결정합니다.
     */
    private fun getOrientation(uri: Uri): Int {
        contentResolver.query(
            uri,
            arrayOf(MediaStore.Images.Media.ORIENTATION),
            null,
            null,
            null
        )?.use { cursor ->
            if (cursor.moveToFirst()) {
                return cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.ORIENTATION))
            }
        }
        return getExifOrientation(uri)
    }

    /**
     * getExifOrientation(): EXIF 데이터를 통해 이미지의 회전 정보를 읽어 실제 각도(0, 90, 180, 270)로 매핑합니다.
     */
    private fun getExifOrientation(uri: Uri): Int =
        contentResolver.openInputStream(uri)?.use { input ->
            ExifInterface(input).getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
        }?.let { orientation ->
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> ORIENTATION_ROTATE_90
                ExifInterface.ORIENTATION_ROTATE_180 -> ORIENTATION_ROTATE_180
                ExifInterface.ORIENTATION_ROTATE_270 -> ORIENTATION_ROTATE_270
                else -> ORIENTATION_NORMAL
            }
        } ?: ORIENTATION_NORMAL

    /**
     * rotateBitmap(): 주어진 회전 각도만큼 비트맵을 회전시키며, 회전 후 원본을 재활용합니다.
     */
    private fun rotateBitmap(bitmap: Bitmap, angle: Int): Bitmap =
        runCatching {
            Matrix().apply {
                postRotate(angle.toFloat())
            }.let { matrix ->
                Bitmap.createBitmap(
                    bitmap,
                    0,
                    0,
                    bitmap.width,
                    bitmap.height,
                    matrix,
                    true
                )
            }
        }.onSuccess { rotatedBitmap ->
            if (rotatedBitmap != bitmap) {
                bitmap.recycle()
            }
        }.getOrDefault(bitmap)

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
        private const val ORIENTATION_NORMAL = 0
        private const val ORIENTATION_ROTATE_90 = 90
        private const val ORIENTATION_ROTATE_180 = 180
        private const val ORIENTATION_ROTATE_270 = 270
        private const val DEFAULT_FILE_NAME = "image.jpg"
    }
}
