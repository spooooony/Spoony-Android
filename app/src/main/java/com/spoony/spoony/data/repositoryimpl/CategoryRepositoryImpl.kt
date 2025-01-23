package com.spoony.spoony.data.repositoryimpl

import com.spoony.spoony.data.datasource.CategoryDataSource
import com.spoony.spoony.data.mapper.toDomain
import com.spoony.spoony.domain.entity.CategoryEntity
import com.spoony.spoony.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDataSource: CategoryDataSource
) : CategoryRepository {
    override suspend fun getCategories(): Result<List<CategoryEntity>> = runCatching {
        categoryDataSource.getCategories().data!!.categoryMonoList.map { it.toDomain() }
    }

    override suspend fun getFoodCategories(): Result<List<CategoryEntity>> = runCatching {
        categoryDataSource.getFoodCategories().data!!.categoryMonoList.map { it.toDomain() }
    }
}
