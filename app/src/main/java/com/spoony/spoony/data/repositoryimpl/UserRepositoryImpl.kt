package com.spoony.spoony.data.repositoryimpl

import com.spoony.spoony.data.datasource.UserRemoteDataSource
import com.spoony.spoony.data.mapper.toDomain
import com.spoony.spoony.domain.entity.BasicUserInfoEntity
import com.spoony.spoony.domain.entity.FollowListEntity
import com.spoony.spoony.domain.entity.RegionEntity
import com.spoony.spoony.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {
    override suspend fun getMyInfo(): Result<BasicUserInfoEntity> =
        runCatching {
            userRemoteDataSource.getMyInfo().data!!.toDomain()
        }

    override suspend fun getUserInfoById(userId: Int): Result<BasicUserInfoEntity> =
        runCatching {
            userRemoteDataSource.getUserInfoById(userId).data!!.toDomain()
        }

    override suspend fun getRegionList(): Result<List<RegionEntity>> =
        runCatching {
            userRemoteDataSource.getRegionList().data!!.regionList.map { it.toDomain() }
        }

    override suspend fun checkUserNameExist(userName: String): Result<Boolean> =
        runCatching {
            userRemoteDataSource.checkUserNameExist(userName).data == true
        }

    override suspend fun followUser(userId: Int): Result<Unit> =
        runCatching {
            userRemoteDataSource.followUser(userId)
        }

    override suspend fun unfollowUser(userId: Int): Result<Unit> =
        runCatching {
            userRemoteDataSource.unfollowUser(userId)
        }

    override suspend fun getMyFollowings(): Result<FollowListEntity> =
        runCatching {
            userRemoteDataSource.getMyFollowings().data!!.toDomain()
        }

    override suspend fun getMyFollowers(): Result<FollowListEntity> =
        runCatching {
            userRemoteDataSource.getMyFollowers().data!!.toDomain()
        }

    override suspend fun getOtherFollowings(targetUserId: Int): Result<FollowListEntity> =
        runCatching {
            userRemoteDataSource.getOtherFollowings(targetUserId).data!!.toDomain()
        }

    override suspend fun getOtherFollowers(targetUserId: Int): Result<FollowListEntity> =
        runCatching {
            userRemoteDataSource.getOtherFollowers(targetUserId).data!!.toDomain()
        }

    override suspend fun blockUser(userId: Int): Result<Unit> =
        runCatching {
            userRemoteDataSource.blockUser(userId)
        }

    override suspend fun unblockUser(userId: Int): Result<Unit> =
        runCatching {
            userRemoteDataSource.unblockUser(userId)
        }
}
