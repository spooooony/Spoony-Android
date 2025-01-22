package com.spoony.spoony.domain.repository

import android.net.Uri
import com.spoony.spoony.domain.entity.CategoryEntity
import com.spoony.spoony.domain.entity.PlaceEntity

interface RegisterRepository {
    suspend fun getFoodCategories(): Result<List<CategoryEntity>>
    suspend fun searchPlace(query: String, display: Int = 5): Result<List<PlaceEntity>>
    suspend fun checkDuplicatePlace(
        userId: Long,
        latitude: Double,
        longitude: Double
    ): Result<Boolean>
    suspend fun registerPost(
        userId: Long,
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
    ): Result<Unit>
}
