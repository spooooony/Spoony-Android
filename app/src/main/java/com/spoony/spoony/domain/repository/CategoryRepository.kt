package com.spoony.spoony.domain.repository

import com.spoony.spoony.domain.entity.CategoryEntity

interface CategoryRepository {
    suspend fun getCategories(): Result<List<CategoryEntity>>
    suspend fun getFoodCategories(): Result<List<CategoryEntity>>
}
