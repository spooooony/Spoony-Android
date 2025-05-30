package com.spoony.spoony.data.datasourceimpl

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.datasource.UserRemoteDataSource
import com.spoony.spoony.data.dto.request.ProfileUpdateRequestDto
import com.spoony.spoony.data.dto.request.TargetUserRequestDto
import com.spoony.spoony.data.dto.response.BasicUserInfoResponseDto
import com.spoony.spoony.data.dto.response.BlockingListResponseDto
import com.spoony.spoony.data.dto.response.FollowListResponseDto
import com.spoony.spoony.data.dto.response.GetRegionListDto
import com.spoony.spoony.data.dto.response.ProfileImageResponseDto
import com.spoony.spoony.data.dto.response.ProfileInfoResponseDto
import com.spoony.spoony.data.service.UserService
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val userService: UserService
) : UserRemoteDataSource {
    override suspend fun getMyInfo(): BaseResponse<BasicUserInfoResponseDto> =
        userService.getMyInfo()

    override suspend fun getUserInfoById(userId: Int): BaseResponse<BasicUserInfoResponseDto> =
        userService.getUserInfoById(userId)

    override suspend fun getRegionList(): BaseResponse<GetRegionListDto> =
        userService.getRegionList()

    override suspend fun checkUserNameExist(userName: String): BaseResponse<Boolean> =
        userService.checkUserNameExist(userName)

    override suspend fun followUser(userId: Int): BaseResponse<Unit> =
        userService.followUser(
            TargetUserRequestDto(targetUserId = userId)
        )

    override suspend fun unfollowUser(userId: Int): BaseResponse<Unit> =
        userService.unfollowUser(
            TargetUserRequestDto(targetUserId = userId)
        )

    override suspend fun getMyFollowings(): BaseResponse<FollowListResponseDto> =
        userService.getMyFollowings()

    override suspend fun getMyFollowers(): BaseResponse<FollowListResponseDto> =
        userService.getMyFollowers()

    override suspend fun getOtherFollowings(targetUserId: Int): BaseResponse<FollowListResponseDto> =
        userService.getOtherFollowings(targetUserId)

    override suspend fun getOtherFollowers(targetUserId: Int): BaseResponse<FollowListResponseDto> =
        userService.getOtherFollowers(targetUserId)

    override suspend fun blockUser(userId: Int): BaseResponse<Unit> =
        userService.blockUser(
            TargetUserRequestDto(targetUserId = userId)
        )

    override suspend fun unblockUser(userId: Int): BaseResponse<Unit> =
        userService.unblockUser(
            TargetUserRequestDto(targetUserId = userId)
        )

    override suspend fun getBlockingList(): BaseResponse<BlockingListResponseDto> =
        userService.getBlockingList()

    override suspend fun getMyProfileInfo(): BaseResponse<ProfileInfoResponseDto> =
        userService.getMyProfileInfo()

    override suspend fun getMyProfileImage(): BaseResponse<ProfileImageResponseDto> =
        userService.getMyProfileImages()

    override suspend fun updateMyProfileInfo(profileUpdate: ProfileUpdateRequestDto): BaseResponse<Unit> =
        userService.updateMyProfileInfo(profileUpdate)
}
