package com.spoony.spoony.presentation.register.model

import com.spoony.spoony.domain.entity.RegisterPostEntity
import com.spoony.spoony.domain.entity.UpdatePostEntity
import com.spoony.spoony.presentation.register.component.SelectedPhoto

data class RegisterPostModel(
    val description: String,
    val value: Float,
    val cons: String?,
    val placeName: String,
    val placeAddress: String,
    val placeRoadAddress: String,
    val latitude: Double,
    val longitude: Double,
    val categoryId: Int,
    val menuList: List<String>,
    val photos: List<String>
)

data class UpdatePostModel(
    val postId: Int,
    val description: String,
    val value: Float,
    val cons: String?,
    val categoryId: Int,
    val menuList: List<String>,
    val deleteImageUrls: List<String>,
    val newPhotos: List<String>
)

fun RegisterPostEntity.toModel(): RegisterPostModel =
    RegisterPostModel(
        description = description,
        value = value,
        cons = cons,
        placeName = placeName,
        placeAddress = placeAddress,
        placeRoadAddress = placeRoadAddress,
        latitude = latitude,
        longitude = longitude,
        categoryId = categoryId,
        menuList = menuList,
        photos = photos
    )

fun UpdatePostEntity.toModel(): UpdatePostModel =
    UpdatePostModel(
        postId = postId,
        description = description,
        value = value,
        cons = cons,
        categoryId = categoryId,
        menuList = menuList,
        deleteImageUrls = deleteImageUrls,
        newPhotos = newPhotos
    )

fun RegisterState.toRegisterPostEntity(): RegisterPostEntity =
    RegisterPostEntity(
        description = detailReview,
        value = userSatisfactionValue,
        cons = optionalReview.takeIf { it.isNotBlank() },
        placeName = selectedPlace.placeName,
        placeAddress = selectedPlace.placeAddress,
        placeRoadAddress = selectedPlace.placeRoadAddress,
        latitude = selectedPlace.latitude,
        longitude = selectedPlace.longitude,
        categoryId = selectedCategory.categoryId,
        menuList = menuList.filter { it.isNotBlank() },
        photos = selectedPhotos.map { it.uri.toString() }
    )

fun RegisterState.toUpdatePostEntity(postId: Int, deleteImageUrls: List<String>): UpdatePostEntity =
    UpdatePostEntity(
        postId = postId,
        description = detailReview,
        value = userSatisfactionValue,
        cons = optionalReview.takeIf { it.isNotBlank() },
        categoryId = selectedCategory.categoryId,
        menuList = menuList.filter { it.isNotBlank() },
        deleteImageUrls = deleteImageUrls,
        newPhotos = selectedPhotos.filter { it.isNewPhoto }.map { it.uri.toString() }
    )

val SelectedPhoto.isNewPhoto: Boolean
    get() = !uri.toString().startsWith("http")
