package com.spoony.spoony.data.datasourceimpl

import com.spoony.spoony.data.datasource.DummyRemoteDataSource
import com.spoony.spoony.data.dto.response.GetDummyUserResponse
import com.spoony.spoony.data.service.DummyService
import javax.inject.Inject

class DummyRemoteDataSourceImpl @Inject constructor(
    private val dummyService: DummyService,
) : DummyRemoteDataSource {
    override suspend fun getDummyUserList(page: Int): GetDummyUserResponse = dummyService.getDummyUser(page = page)
}
