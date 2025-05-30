package com.spoony.spoony.data.service

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.dto.response.GetRegionListDto
import retrofit2.http.GET

interface RegionService {
    @GET("/api/v1/user/region")
    suspend fun getRegionList(): BaseResponse<GetRegionListDto>
}
