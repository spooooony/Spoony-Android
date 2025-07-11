package com.spoony.spoony.domain.repository

import com.spoony.spoony.domain.entity.BasicUserInfoEntity
import com.spoony.spoony.domain.entity.BlockingListEntity
import com.spoony.spoony.domain.entity.FollowListEntity
import com.spoony.spoony.domain.entity.ProfileImageEntity
import com.spoony.spoony.domain.entity.ProfileInfoEntity
import com.spoony.spoony.domain.entity.ProfileUpdateEntity
import com.spoony.spoony.domain.entity.RegionEntity

interface UserRepository {
    suspend fun getMyInfo(): Result<BasicUserInfoEntity>

    suspend fun getUserInfoById(userId: Int): Result<BasicUserInfoEntity>

    suspend fun getRegionList(): Result<List<RegionEntity>>

    suspend fun checkUserNameExist(userName: String): Result<Boolean>

    suspend fun followUser(userId: Int): Result<Unit>

    suspend fun unfollowUser(userId: Int): Result<Unit>

    suspend fun getMyFollowings(): Result<FollowListEntity>

    suspend fun getMyFollowers(): Result<FollowListEntity>

    suspend fun getOtherFollowings(targetUserId: Int): Result<FollowListEntity>

    suspend fun getOtherFollowers(targetUserId: Int): Result<FollowListEntity>

    suspend fun blockUser(userId: Int): Result<Unit>

    suspend fun unblockUser(userId: Int): Result<Unit>

    suspend fun getBlockingList(): Result<BlockingListEntity>

    suspend fun getMyProfileInfo(): Result<ProfileInfoEntity>

    suspend fun getMyProfileImage(): Result<ProfileImageEntity>

    suspend fun updateMyProfileInfo(profileUpdate: ProfileUpdateEntity): Result<Unit>
}
