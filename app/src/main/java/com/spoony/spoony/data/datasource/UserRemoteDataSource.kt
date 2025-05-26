package com.spoony.spoony.data.datasource

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.dto.response.BasicUserInfoResponseDto
import com.spoony.spoony.data.dto.response.GetRegionListDto

interface UserRemoteDataSource {
    suspend fun getMyInfo(): BaseResponse<BasicUserInfoResponseDto>

    suspend fun getUserInfoById(userId: Int): BaseResponse<BasicUserInfoResponseDto>
    suspend fun getRegionList(): BaseResponse<GetRegionListDto>
    suspend fun checkUserNameExist(userName: String): BaseResponse<Boolean>
}
