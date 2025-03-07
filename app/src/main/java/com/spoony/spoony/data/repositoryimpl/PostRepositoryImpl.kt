package com.spoony.spoony.data.repositoryimpl

import com.spoony.spoony.data.datasource.PostRemoteDataSource
import com.spoony.spoony.data.mapper.toDomain
import com.spoony.spoony.domain.entity.AddedMapPostEntity
import com.spoony.spoony.domain.entity.PostEntity
import com.spoony.spoony.domain.repository.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postRemoteDataSource: PostRemoteDataSource
) : PostRepository {
    override suspend fun getPost(postId: Int): Result<PostEntity> =
        runCatching {
            postRemoteDataSource.getPostData(postId).data!!.toDomain()
        }

    override suspend fun postScoopPost(postId: Int): Result<Boolean> =
        runCatching {
            postRemoteDataSource.postScoopPost(postId = postId).success
        }

    override suspend fun postAddMap(postId: Int): Result<Boolean> =
        runCatching {
            postRemoteDataSource.postAddMapData(postId).success
        }

    override suspend fun deletePinMap(postId: Int): Result<Boolean> =
        runCatching {
            postRemoteDataSource.deletePinMap(postId = postId).success
        }

    override suspend fun getAddedMapPost(placeId: Int): Result<List<AddedMapPostEntity>> =
        runCatching {
            postRemoteDataSource.getAddedMapPost(
                postId = placeId
            ).data?.zzimFocusResponseList!!.map { it.toDomain() }
        }
}
