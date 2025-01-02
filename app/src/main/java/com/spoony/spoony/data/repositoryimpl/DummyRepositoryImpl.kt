package com.spoony.spoony.data.repositoryimpl

import com.spoony.spoony.data.datasource.DummyRemoteDataSource
import com.spoony.spoony.data.mapper.toDummyEntity
import com.spoony.spoony.domain.entity.DummyEntity
import com.spoony.spoony.domain.repository.DummyRepository
import javax.inject.Inject

class DummyRepositoryImpl @Inject constructor(
    private val dummyRemoteDataSource: DummyRemoteDataSource,
) : DummyRepository {
    override suspend fun getDummyUser(page: Int): Result<DummyEntity> =
        runCatching {
            dummyRemoteDataSource.getDummyUserList(page = page).toDummyEntity()
        }
}
