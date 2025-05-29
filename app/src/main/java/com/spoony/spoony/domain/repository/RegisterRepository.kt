package com.spoony.spoony.domain.repository

import com.spoony.spoony.domain.entity.CategoryEntity
import com.spoony.spoony.domain.entity.PlaceEntity
import com.spoony.spoony.domain.entity.PlaceReviewEntity
import com.spoony.spoony.domain.entity.RegisterPostEntity
import com.spoony.spoony.domain.entity.UpdatePostEntity

interface RegisterRepository {
    suspend fun getFoodCategories(): Result<List<CategoryEntity>>
    suspend fun searchPlace(query: String, display: Int = 5): Result<List<PlaceEntity>>
    suspend fun checkDuplicatePlace(latitude: Double, longitude: Double): Result<Boolean>
    suspend fun registerPost(registerPostEntity: RegisterPostEntity): Result<Unit>
    suspend fun getPostDetail(postId: Int): Result<PlaceReviewEntity>
    suspend fun updatePost(updatePostEntity: UpdatePostEntity): Result<Unit>
}
