package com.spoony.spoony.domain.repository

import com.spoony.spoony.domain.entity.DummyEntity

interface DummyRepository {
    suspend fun getDummyUser(page: Int): Result<DummyEntity>
}
