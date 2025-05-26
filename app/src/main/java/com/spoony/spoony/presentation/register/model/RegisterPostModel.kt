package com.spoony.spoony.presentation.register.model

import com.spoony.spoony.domain.entity.RegisterPostEntity
import com.spoony.spoony.domain.entity.UpdatePostEntity
import com.spoony.spoony.presentation.register.component.SelectedPhoto

val SelectedPhoto.isNewPhoto: Boolean
    get() = !uri.toString().startsWith("http")

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
