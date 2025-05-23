package com.spoony.spoony.data.datasourceimpl

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.datasource.UserRemoteDataSource
import com.spoony.spoony.data.dto.response.GetRegionListDto
import com.spoony.spoony.data.dto.response.UserInfoResponseDto
import com.spoony.spoony.data.service.UserService
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val userService: UserService
) : UserRemoteDataSource {
    override suspend fun getUserInfoById(userId: Int): BaseResponse<UserInfoResponseDto> =
        userService.getUserInfoById(userId)

    override suspend fun getRegionList(): BaseResponse<GetRegionListDto> =
        userService.getRegionList()
}
