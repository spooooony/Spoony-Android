package com.spoony.spoony.data.service

import com.spoony.spoony.data.dto.response.GetDummyUserResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DummyService {
    @GET("api/users/{page}")
    suspend fun getDummyUser(
        @Path("page") page: Int,
    ): GetDummyUserResponse
}
