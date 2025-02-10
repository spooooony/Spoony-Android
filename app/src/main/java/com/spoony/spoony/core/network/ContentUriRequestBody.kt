package com.spoony.spoony.core.network

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Size
import androidx.annotation.RequiresApi
import java.io.ByteArrayOutputStream
import javax.inject.Inject
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
    private val uri: Uri?
) : RequestBody() {
    private val contentResolver = context.contentResolver
    private var compressedImage: ByteArray? = null
    private var metadata: ImageMetadata? = null

    private data class ImageMetadata private constructor(
        val fileName: String,
        val size: Long = 0L,
        val mimeType: String?
    ) {
        companion object {
            fun create(fileName: String, size: Long, mimeType: String?) =
                ImageMetadata(fileName, size, mimeType)
        }
    }

    init {
        uri?.let {
            metadata = extractMetadata(it)
        }
    }

    private fun extractMetadata(uri: Uri): ImageMetadata {
        var fileName = ""
        var size = 0L

        contentResolver.query(
            uri,
            arrayOf(MediaStore.Images.Media.SIZE, MediaStore.Images.Media.DISPLAY_NAME),
            null,
            null,
            null
        )?.use { cursor ->
            if (cursor.moveToFirst()) {
                size = cursor.getLong(
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)
                )
                fileName = cursor.getString(
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
                )
            }
        }

        return ImageMetadata.create(
            fileName = fileName,
            size = size,
            mimeType = contentResolver.getType(uri)
        )
    }

    suspend fun prepareImage() = withContext(Dispatchers.IO) {
        uri?.let { safeUri ->
            runCatching {
                compressedImage = compressImage(safeUri)
            }.onFailure { error ->
                Timber.e(error, "이미지 압축에 실패했습니다.")
                throw error
            }
        }
    }

    private suspend fun compressImage(uri: Uri): ByteArray = withContext(Dispatchers.IO) {
        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            loadBitmapWithImageDecoder(uri)
        } else {
            loadBitmapLegacy(uri)
        }

        compressBitmap(bitmap).also {
            bitmap.recycle()
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private suspend fun loadBitmapWithImageDecoder(uri: Uri): Bitmap = withContext(Dispatchers.IO) {
        val source = ImageDecoder.createSource(contentResolver, uri)
        ImageDecoder.decodeBitmap(source) { decoder, info, _ ->
            decoder.allocator = ImageDecoder.ALLOCATOR_SOFTWARE
            decoder.isMutableRequired = true

            val size = calculateTargetSize(info.size.width, info.size.height)
            decoder.setTargetSize(size.width, size.height)
        }
    }

    private suspend fun loadBitmapLegacy(uri: Uri): Bitmap = withContext(Dispatchers.IO) {
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }

        requireNotNull(
            contentResolver.openInputStream(uri)?.use { input ->
                BitmapFactory.decodeStream(input, null, options)
                options.apply {
                    inJustDecodeBounds = false
                    inSampleSize = calculateInSampleSize(this, MAX_WIDTH, MAX_HEIGHT)
                    inPreferredConfig = Bitmap.Config.ARGB_8888
                }

                contentResolver.openInputStream(uri)?.use { secondInput ->
                    BitmapFactory.decodeStream(secondInput, null, options)
                }
            }
        ) { "비트맵 디코딩 실패" }
    }.let { bitmap ->
        val orientation = getOrientation(uri)
        if (orientation != ORIENTATION_NORMAL) {
            rotateBitmap(bitmap, orientation)
        } else {
            bitmap
        }
    }

    private fun getOrientation(uri: Uri): Int =
        contentResolver.query(
            uri,
            arrayOf(MediaStore.Images.Media.ORIENTATION),
            null,
            null,
            null
        )?.use {
            if (it.moveToFirst()) {
                it.getInt(it.getColumnIndexOrThrow(MediaStore.Images.Media.ORIENTATION))
            } else {
                ORIENTATION_NORMAL
            }
        } ?: getExifOrientation(uri)

    private fun getExifOrientation(uri: Uri): Int =
        contentResolver.openInputStream(uri)?.use { input ->
            val exif = ExifInterface(input)
            when (
                exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
                )
            ) {
                ExifInterface.ORIENTATION_ROTATE_90 -> ORIENTATION_ROTATE_90
                ExifInterface.ORIENTATION_ROTATE_180 -> ORIENTATION_ROTATE_180
                ExifInterface.ORIENTATION_ROTATE_270 -> ORIENTATION_ROTATE_270
                else -> ORIENTATION_NORMAL
            }
        } ?: ORIENTATION_NORMAL

    private fun rotateBitmap(bitmap: Bitmap, angle: Int): Bitmap =
        Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.width,
            bitmap.height,
            Matrix().apply { postRotate(angle.toFloat()) },
            true
        ).also {
            if (it != bitmap) {
                bitmap.recycle()
            }
        }

    private suspend fun compressBitmap(bitmap: Bitmap): ByteArray = withContext(Dispatchers.IO) {
        val maxFileSize = MAX_FILE_SIZE_BYTES
        var lowerQuality = MIN_QUALITY // 최소 품질 (예: 20)
        var upperQuality = INITIAL_QUALITY // 초기 품질 (예: 100)
        var bestQuality = lowerQuality // 조건을 만족하는 최고 품질 값
        var bestByteArray = ByteArray(0)

        // 이진 탐색을 통해 파일 크기가 maxFileSize 이하가 되는 최대 품질을 찾음
        while (lowerQuality <= upperQuality) {
            val midQuality = (lowerQuality + upperQuality) / 2

            // 임시 ByteArrayOutputStream에 bitmap을 midQuality로 압축
            val byteArray = ByteArrayOutputStream().use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, midQuality, outputStream)
                outputStream.toByteArray()
            }
            val size = byteArray.size

            if (size <= maxFileSize) {
                // 압축 결과가 1MB 이하이면, 더 높은 품질을 시도하기 위해 하한선을 올림
                bestQuality = midQuality
                bestByteArray = byteArray
                lowerQuality = midQuality + 1
            } else {
                // 파일 크기가 너무 크면, 상한선을 낮춤
                upperQuality = midQuality - 1
            }
        }

        Timber.d("선택된 품질: $bestQuality, 압축된 이미지 크기: ${bestByteArray.size} 바이트")
        bestByteArray
    }

    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int
    ): Int {
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            while (halfHeight / inSampleSize >= reqHeight &&
                halfWidth / inSampleSize >= reqWidth
            ) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }

    private fun calculateTargetSize(width: Int, height: Int): Size {
        val ratio = width.toFloat() / height.toFloat()
        return if (width > height) {
            Size(MAX_WIDTH, (MAX_WIDTH / ratio).toInt())
        } else {
            Size((MAX_HEIGHT * ratio).toInt(), MAX_HEIGHT)
        }
    }

    override fun contentLength(): Long = compressedImage?.size?.toLong() ?: -1L

    override fun contentType(): MediaType? = metadata?.mimeType?.toMediaTypeOrNull()

    override fun writeTo(sink: BufferedSink) {
        compressedImage?.let(sink::write)
    }

    fun toFormData(name: String): MultipartBody.Part = MultipartBody.Part.createFormData(
        name,
        metadata?.fileName ?: DEFAULT_FILE_NAME,
        this
    )

    private companion object {
        // 이미지 크기 관련
        private const val MAX_WIDTH = 1024
        private const val MAX_HEIGHT = 1024
        private const val MAX_FILE_SIZE_BYTES = 1024 * 1024 // 1MB

        // 압축 품질 관련
        private const val INITIAL_QUALITY = 100
        private const val MIN_QUALITY = 20

        // 이미지 회전 관련
        private const val ORIENTATION_NORMAL = 0
        private const val ORIENTATION_ROTATE_90 = 90
        private const val ORIENTATION_ROTATE_180 = 180
        private const val ORIENTATION_ROTATE_270 = 270

        // 기타
        private const val DEFAULT_FILE_NAME = "image.jpg"
    }
}

class ContentUriRequestBodyLegacy(
    context: Context,
    private val uri: Uri?
) : RequestBody() {
    private val contentResolver = context.contentResolver

    private var fileName = ""
    private var size = -1L
    private var compressedImage: ByteArray? = null

    init {
        if (uri != null) {
            contentResolver.query(
                uri,
                arrayOf(MediaStore.Images.Media.SIZE, MediaStore.Images.Media.DISPLAY_NAME),
                null,
                null,
                null
            )?.use { cursor ->
                if (cursor.moveToFirst()) {
                    size =
                        cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE))
                    fileName =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                }
            }

            compressBitmap()
        }
    }

    private fun compressBitmap() {
        if (uri != null) {
            var originalBitmap: Bitmap
            val exif: ExifInterface

            contentResolver.openInputStream(uri).use { inputStream ->
                if (inputStream == null) return
                val option = BitmapFactory.Options().apply {
                    inSampleSize = calculateInSampleSize(this, MAX_WIDTH, MAX_HEIGHT)
                }
                originalBitmap = BitmapFactory.decodeStream(inputStream, null, option) ?: return
                exif = ExifInterface(inputStream)
            }

            var orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> orientation = 90
                ExifInterface.ORIENTATION_ROTATE_180 -> orientation = 180
                ExifInterface.ORIENTATION_ROTATE_270 -> orientation = 270
            }

            if (orientation >= 90) {
                val matrix = Matrix().apply {
                    setRotate(orientation.toFloat())
                }

                val rotatedBitmap = Bitmap.createBitmap(
                    originalBitmap,
                    0,
                    0,
                    originalBitmap.width,
                    originalBitmap.height,
                    matrix,
                    true
                )
                originalBitmap.recycle()
                originalBitmap = rotatedBitmap
            }

            val outputStream = ByteArrayOutputStream()
            val imageSizeMb = size / (MAX_WIDTH * MAX_HEIGHT.toDouble())
            outputStream.use {
                val compressRate = ((IMAGE_SIZE_MB / imageSizeMb) * 100).toInt()
                originalBitmap.compress(
                    Bitmap.CompressFormat.JPEG,
                    if (imageSizeMb >= IMAGE_SIZE_MB) compressRate else 100,
                    it
                )
            }
            compressedImage = outputStream.toByteArray()
            size = compressedImage?.size?.toLong() ?: -1L
        }
    }

    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int
    ): Int {
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }

    private fun getFileName() = fileName

    override fun contentLength(): Long = size

    override fun contentType(): MediaType? =
        uri?.let { contentResolver.getType(it)?.toMediaTypeOrNull() }

    override fun writeTo(sink: BufferedSink) {
        compressedImage?.let(sink::write)
    }

    fun toFormData(name: String) = MultipartBody.Part.createFormData(name, getFileName(), this)

    companion object {
        const val IMAGE_SIZE_MB = 1
        const val MAX_WIDTH = 1024
        const val MAX_HEIGHT = 1024
    }
}
