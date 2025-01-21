package com.spoony.spoony.domain.repository

import com.spoony.spoony.domain.entity.CategoryEntity

interface ExploreRepository {
    suspend fun getCategoryList(): Result<List<CategoryEntity>>
}
