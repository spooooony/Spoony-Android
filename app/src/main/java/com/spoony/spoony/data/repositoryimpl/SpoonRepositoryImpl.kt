package com.spoony.spoony.data.repositoryimpl

import com.spoony.spoony.data.datasource.SpoonDataSource
import com.spoony.spoony.data.mapper.toDomain
import com.spoony.spoony.domain.entity.SpoonEntity
import com.spoony.spoony.domain.entity.SpoonListEntity
import com.spoony.spoony.domain.repository.SpoonRepository
import javax.inject.Inject

class SpoonRepositoryImpl @Inject constructor(
    private val spoonDataSource: SpoonDataSource
) : SpoonRepository {
    override suspend fun drawSpoon(): Result<SpoonEntity> = runCatching {
        spoonDataSource.drawSpoon().data!!.toDomain()
    }

    override suspend fun getWeeklySpoonDraw(): Result<SpoonListEntity> = runCatching {
        spoonDataSource.getWeeklySpoonDraw().data!!.toDomain()
    }

    override suspend fun getSpoonCount(): Result<Int> = runCatching {
        spoonDataSource.getSpoonCount().data!!.spoonAmount
    }
}
