package com.spoony.spoony.data.repositoryimpl

import com.spoony.spoony.data.datasource.TooltipPreferencesDataSource
import com.spoony.spoony.domain.repository.TooltipPreferencesRepository
import kotlinx.coroutines.flow.Flow

class TooltipPreferencesRepositoryImpl(
    private val dataSource: TooltipPreferencesDataSource
) : TooltipPreferencesRepository {
    override fun isTooltipShown(): Flow<Boolean> = dataSource.isTooltipShown

    override suspend fun disableTooltip() {
        dataSource.disableTooltip()
    }
}
