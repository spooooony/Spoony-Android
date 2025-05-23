package com.spoony.spoony.data.datasource

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.dto.response.GetRegionListDto
import com.spoony.spoony.data.dto.response.UserInfoResponseDto

interface UserRemoteDataSource {
    suspend fun getUserInfoById(userId: Int): BaseResponse<UserInfoResponseDto>
    suspend fun getRegionList(): BaseResponse<GetRegionListDto>
}
