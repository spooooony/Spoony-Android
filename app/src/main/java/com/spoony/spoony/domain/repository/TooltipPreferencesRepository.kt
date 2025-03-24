package com.spoony.spoony.domain.repository

import kotlinx.coroutines.flow.Flow

interface TooltipPreferencesRepository {
    fun isTooltipShown(): Flow<Boolean>
    suspend fun setTooltipShown(shown: Boolean)
}
