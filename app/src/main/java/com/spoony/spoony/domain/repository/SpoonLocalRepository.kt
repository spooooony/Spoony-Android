package com.spoony.spoony.domain.repository

interface SpoonLocalRepository {
    suspend fun getSpoonDrawLog(): Pair<String?, Boolean?>
    suspend fun updateLastEntryDate(date: String)
    suspend fun updateSpoonDrawn()
}
