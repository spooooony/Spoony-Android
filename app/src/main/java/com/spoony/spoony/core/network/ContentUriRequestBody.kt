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

    suspend fun prepareImage(): Result<Unit> = runCatching {
        withContext(Dispatchers.IO) {
            uri?.let { safeUri ->
                compressImage(safeUri).onSuccess { bytes ->
                    compressedImage = bytes
                }
            }
        }
    }

    fun toFormData(name: String): MultipartBody.Part = MultipartBody.Part.createFormData(
        name,
        metadata?.fileName ?: DEFAULT_FILE_NAME,
        this
    )

    private suspend fun compressImage(uri: Uri): Result<ByteArray> =
        withContext(Dispatchers.IO) {
            loadBitmap(uri).map { bitmap ->
                compressBitmap(bitmap).also {
                    bitmap.recycle()
                }
            }
        }

    private suspend fun loadBitmap(uri: Uri): Result<Bitmap> =
        withContext(Dispatchers.IO) {
            runCatching {
                val source = ImageDecoder.createSource(contentResolver, uri)
                ImageDecoder.decodeBitmap(source) { decoder, info, _ ->
                    decoder.allocator = ImageDecoder.ALLOCATOR_SOFTWARE
                    decoder.isMutableRequired = true
                    calculateTargetSize(info.size.width, info.size.height).let { size ->
                        decoder.setTargetSize(size.width, size.height)
                    }
                }
            }.map { bitmap ->
                getOrientation(uri).let { orientation ->
                    if (orientation != ORIENTATION_NORMAL) {
                        rotateBitmap(bitmap, orientation)
                    } else {
                        bitmap
                    }
                }
            }
        }

    private suspend fun compressBitmap(bitmap: Bitmap): ByteArray =
        withContext(Dispatchers.IO) {
            val estimatedSize = min(bitmap.byteCount / 4, config.maxFileSize)
            ByteArrayOutputStream(estimatedSize).use { buffer ->
                var lowerQuality = config.minQuality
                var upperQuality = config.initialQuality
                var bestQuality = lowerQuality

                while (lowerQuality <= upperQuality) {
                    val midQuality = (lowerQuality + upperQuality) / 2
                    buffer.reset()

                    bitmap.compress(config.format, midQuality, buffer)

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

    private fun calculateTargetSize(width: Int, height: Int): Size {
        val ratio = width.toFloat() / height.toFloat()
        return if (width > height) {
            Size(config.maxWidth, (config.maxWidth / ratio).toInt())
        } else {
            Size((config.maxHeight * ratio).toInt(), config.maxHeight)
        }
    }

    override fun contentLength(): Long = compressedImage?.size?.toLong() ?: -1L

    override fun contentType(): MediaType? = metadata?.mimeType?.toMediaTypeOrNull()

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
