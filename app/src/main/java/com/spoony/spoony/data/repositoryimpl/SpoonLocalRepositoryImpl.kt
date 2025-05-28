package com.spoony.spoony.data.repositoryimpl

import com.spoony.spoony.data.datasource.local.SpoonLocalDataSource
import com.spoony.spoony.domain.repository.SpoonLocalRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.firstOrNull

class SpoonLocalRepositoryImpl @Inject constructor(
    private val spoonLocalDataSource: SpoonLocalDataSource
) : SpoonLocalRepository {
    override suspend fun getSpoonDrawLog(): Pair<String?, Boolean?> {
        val lastEntryDate = spoonLocalDataSource.getLastEntryDate().firstOrNull()
        val isSpoonDrawn = spoonLocalDataSource.getIsSpoonDrawn().firstOrNull()
        return lastEntryDate to isSpoonDrawn
    }

    override suspend fun updateLastEntryDate(date: String) {
        spoonLocalDataSource.updateLastEntryDate(date)
        spoonLocalDataSource.updateSpoonDrawn(false)
    }

    override suspend fun updateSpoonDrawn() = spoonLocalDataSource.updateSpoonDrawn(true)
}
