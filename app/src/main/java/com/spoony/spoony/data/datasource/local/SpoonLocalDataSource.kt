package com.spoony.spoony.data.datasource.local

import kotlinx.coroutines.flow.Flow

interface SpoonLocalDataSource {
    fun getLastEntryDate(): Flow<String?>
    fun getIsSpoonDrawn(): Flow<Boolean>
    suspend fun updateLastEntryDate(date: String)
    suspend fun updateSpoonDrawn(isSpoonDrawn: Boolean)
}
