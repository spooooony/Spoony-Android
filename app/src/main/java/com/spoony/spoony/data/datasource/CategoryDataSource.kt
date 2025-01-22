package com.spoony.spoony.data.datasource

import com.spoony.spoony.data.dto.base.BaseResponse
import com.spoony.spoony.data.dto.response.CategoryResponseDto
import com.spoony.spoony.data.dto.response.FoodCategoryResponseDto

interface CategoryDataSource {
    suspend fun getCategories(): BaseResponse<CategoryResponseDto>
    suspend fun getFoodCategories(): BaseResponse<FoodCategoryResponseDto>
}
