package com.spoony.spoony.data.datasource

import com.spoony.spoony.data.dto.response.GetDummyUserResponse

interface DummyRemoteDataSource {
    suspend fun getDummyUserList(page: Int): GetDummyUserResponse
}
