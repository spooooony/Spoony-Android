package com.spoony.spoony.data.repositoryimpl

import com.spoony.spoony.data.datasource.SpoonDataSource
import com.spoony.spoony.data.datasource.local.SpoonLocalDataSource
import com.spoony.spoony.data.mapper.toDomain
import com.spoony.spoony.domain.entity.SpoonEntity
import com.spoony.spoony.domain.entity.SpoonListEntity
import com.spoony.spoony.domain.repository.SpoonRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

class SpoonRepositoryImpl @Inject constructor(
    private val spoonDataSource: SpoonDataSource,
    private val spoonLocalDataSource: SpoonLocalDataSource
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

    override suspend fun getSpoonDrawLog(): Pair<String?, Boolean> {
        val lastEntryDate = spoonLocalDataSource.getLastEntryDate().firstOrNull()
        val isSpoonDrawn = spoonLocalDataSource.getIsSpoonDrawn().first()
        return lastEntryDate to isSpoonDrawn
    }

    override suspend fun updateLastEntryDate(date: String) {
        spoonLocalDataSource.updateLastEntryDate(date)
        spoonLocalDataSource.updateSpoonDrawn(false)
    }

    override suspend fun updateSpoonDrawn() = spoonLocalDataSource.updateSpoonDrawn(true)
}
