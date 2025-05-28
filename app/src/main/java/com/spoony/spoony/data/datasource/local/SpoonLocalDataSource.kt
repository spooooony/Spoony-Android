package com.spoony.spoony.data.datasource.local

import kotlinx.coroutines.flow.Flow

interface SpoonLocalDataSource {
    suspend fun getLastEntryDate(): Flow<String?>
    suspend fun getIsSpoonDrawn(): Flow<Boolean?>
    suspend fun updateLastEntryDate(date: String)
    suspend fun updateSpoonDrawn(isSpoonDrawn: Boolean)
}
