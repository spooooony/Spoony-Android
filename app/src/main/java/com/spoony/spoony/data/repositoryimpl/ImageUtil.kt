package com.spoony.spoony.data.repositoryimpl

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okio.BufferedSink

object ImageUtil {
    private const val MAX_FILE_SIZE = 2 * 1024 * 1024
    private const val INITIAL_QUALITY = 100

    fun resizeImage(context: Context, uri: Uri): File {
        val bitmap = getBitmapFromUri(context, uri)
        return saveBitmapToFile(context, bitmap)
    }

    private fun getBitmapFromUri(context: Context, uri: Uri): Bitmap {
        return MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
    }

    private fun saveBitmapToFile(context: Context, bitmap: Bitmap): File {
        val file = File(context.cacheDir, "compressed_${System.currentTimeMillis()}.jpg")
        var quality = INITIAL_QUALITY
        var fileSize: Long

        do {
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos)
            val out = FileOutputStream(file)
            out.write(bos.toByteArray())
            out.close()

            fileSize = file.length()
            quality -= 5
        } while (fileSize > MAX_FILE_SIZE && quality > 5)

        return file
    }

    fun createMultipartBody(file: File): MultipartBody.Part {
        val requestFile = file.asRequestBody("image/*".toMediaType())
        return MultipartBody.Part.createFormData("images", file.name, requestFile)
    }
}

class ContentUriRequestBody(
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

            contentResolver.openInputStream(uri).use { inputStream ->
                if (inputStream == null) return
                val option = BitmapFactory.Options().apply {
                    inSampleSize = calculateInSampleSize(this, MAX_WIDTH, MAX_HEIGHT)
                }
                originalBitmap = BitmapFactory.decodeStream(inputStream, null, option) ?: return
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
