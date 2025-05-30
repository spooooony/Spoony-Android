package com.spoony.spoony.domain.repository

import com.spoony.spoony.domain.entity.SpoonEntity
import com.spoony.spoony.domain.entity.SpoonListEntity

interface SpoonRepository {
    suspend fun drawSpoon(): Result<SpoonEntity>
    suspend fun getWeeklySpoonDraw(): Result<SpoonListEntity>
    suspend fun getSpoonCount(): Result<Int>

    suspend fun getSpoonDrawLog(): Pair<String?, Boolean>
    suspend fun updateLastEntryDate(date: String)
    suspend fun updateSpoonDrawn()
}
