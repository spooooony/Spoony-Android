package com.spoony.spoony.data.datasourceimpl

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.datasource.CategoryDataSource
import com.spoony.spoony.data.dto.response.CategoryResponseDto
import com.spoony.spoony.data.dto.response.FoodCategoryResponseDto
import com.spoony.spoony.data.service.CategoryService
import javax.inject.Inject

class CategoryDataSourceImpl @Inject constructor(
    private val categoryService: CategoryService
) : CategoryDataSource {
    override suspend fun getCategories(): BaseResponse<CategoryResponseDto> =
        categoryService.getCategories()

    override suspend fun getFoodCategories(): BaseResponse<FoodCategoryResponseDto> =
        categoryService.getFoodCategories()
}
