package com.spoony.spoony.domain.repository

import com.spoony.spoony.domain.entity.AddedMapPostEntity
import com.spoony.spoony.domain.entity.PostEntity

interface PostRepository {
    suspend fun getPost(postId: Int): Result<PostEntity>

    suspend fun postScoopPost(postId: Int): Result<Boolean>

    suspend fun postAddMap(postId: Int): Result<Boolean>

    suspend fun deletePinMap(postId: Int): Result<Boolean>

    suspend fun getAddedMapPost(placeId: Int): Result<List<AddedMapPostEntity>>
}
