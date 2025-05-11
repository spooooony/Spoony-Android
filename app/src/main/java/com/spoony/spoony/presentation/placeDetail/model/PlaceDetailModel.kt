package com.spoony.spoony.presentation.placeDetail.model

import com.spoony.spoony.domain.entity.PlaceReviewEntity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

data class PlaceDetailModel(
    val photoUrlList: ImmutableList<String>,
    val createdAt: String,
    val menuList: ImmutableList<String>,
    val description: String,
    val value: Double,
    val cons: String,
    val placeName: String,
    val placeAddress: String,
    val latitude: Double,
    val longitude: Double,
    val isMine: Boolean
)

fun PlaceReviewEntity.toModel(): PlaceDetailModel = PlaceDetailModel(
    photoUrlList = this.photoUrlList?.toImmutableList() ?: persistentListOf(),
    createdAt = this.createdAt ?: "",
    menuList = this.menuList?.toImmutableList() ?: persistentListOf(),
    description = this.description,
    value = this.value ?: 0.5,
    cons = this.cons ?: "",
    placeName = this.placeName ?: "",
    placeAddress = this.placeAddress ?: "",
    latitude = this.latitude ?: 0.0,
    longitude = this.longitude ?: 0.0,
    isMine = this.isMine ?: false
)
