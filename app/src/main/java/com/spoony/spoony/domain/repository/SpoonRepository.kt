package com.spoony.spoony.domain.repository

import com.spoony.spoony.domain.entity.SpoonEntity
import com.spoony.spoony.domain.entity.SpoonListEntity

interface SpoonRepository {
    suspend fun drawSpoon(): Result<SpoonEntity>
    suspend fun getWeeklySpoonDraw(): Result<SpoonListEntity>
}
