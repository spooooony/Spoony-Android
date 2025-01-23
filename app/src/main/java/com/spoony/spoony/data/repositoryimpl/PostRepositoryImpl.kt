package com.spoony.spoony.data.repositoryimpl

import com.spoony.spoony.data.datasource.PostRemoteDataSource
import com.spoony.spoony.data.mapper.toDomain
import com.spoony.spoony.domain.entity.PostEntity
import com.spoony.spoony.domain.repository.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postRemoteDataSource: PostRemoteDataSource
) : PostRepository {
    override suspend fun getPost(postId: Int, userId: Int): Result<PostEntity> =
        runCatching {
            postRemoteDataSource.getPostData(postId, userId).data!!.toDomain()
        }

    override suspend fun postScoopPost(postId: Int, userId: Int): Result<Boolean> =
        runCatching {
            postRemoteDataSource.postScoopPost(postId = postId, userId = userId).success
        }

    override suspend fun postAddMap(postId: Int, userId: Int): Result<Boolean> =
        Result.success(
            true
        )

    override suspend fun deletePinMap(postId: Int, userId: Int): Result<Boolean> =
        Result.success(
            true
        )
}
