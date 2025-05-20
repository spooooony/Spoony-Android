package com.spoony.spoony.domain.repository

import com.spoony.spoony.domain.entity.SpoonEntity

interface SpoonRepository {
    suspend fun drawSpoon(): Result<SpoonEntity>
}
