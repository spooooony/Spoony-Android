package com.spoony.spoony.data.service

import com.spoony.spoony.data.dto.base.BaseResponse
import com.spoony.spoony.data.dto.response.CategoryResponseDto
import com.spoony.spoony.data.dto.response.FoodCategoryResponseDto
import retrofit2.http.GET

interface CategoryService {
    @GET("/api/v1/post/categories")
    suspend fun getCategories(): BaseResponse<CategoryResponseDto>

    @GET("/api/v1/post/categories/food")
    suspend fun getFoodCategories(): BaseResponse<FoodCategoryResponseDto>
}
