package com.spoony.spoony.presentation.register.model

import androidx.core.net.toUri
import com.spoony.spoony.domain.entity.PlaceReviewEntity
import com.spoony.spoony.presentation.register.component.SelectedPhoto
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class PlaceReviewModel(
    val reviewId: Int,
    val userId: Int,
    val photoUrls: ImmutableList<String>,
    val date: String,
    val menuList: ImmutableList<String>,
    val description: String,
    val value: Double,
    val cons: String?,
    val placeName: String,
    val placeAddress: String,
    val latitude: Double,
    val longitude: Double,
    val category: CategoryState
)

fun PlaceReviewEntity.toModel(): PlaceReviewModel =
    PlaceReviewModel(
        reviewId = reviewId ?: 0,
        userId = userId ?: 0,
        photoUrls = (photoUrlList ?: emptyList()).toImmutableList(),
        date = createdAt ?: "",
        menuList = (menuList ?: emptyList()).toImmutableList(),
        description = description,
        value = value ?: 0.0,
        cons = cons,
        placeName = placeName ?: "",
        placeAddress = placeAddress ?: "",
        latitude = latitude ?: 0.0,
        longitude = longitude ?: 0.0,
        category = category?.toModel() ?: CategoryState(0, "", "", "")
    )

fun PlaceReviewModel.toRegisterState(currentState: RegisterState): RegisterState =
    currentState.copy(
        selectedPlace = PlaceState(
            placeName = placeName,
            placeAddress = placeAddress,
            placeRoadAddress = placeAddress,
            latitude = latitude,
            longitude = longitude
        ),
        selectedCategory = category,
        menuList = menuList,
        detailReview = description,
        optionalReview = cons ?: "",
        userSatisfactionValue = value.toFloat(),
        originalPhotoUrls = photoUrls,
        selectedPhotos = photoUrls.map { url ->
            SelectedPhoto(uri = url.toUri(), isFromServer = true)
        }.toImmutableList()
    )
