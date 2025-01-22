package com.spoony.spoony.domain.repository

import com.spoony.spoony.domain.entity.PostEntity

interface PostRepository {
    suspend fun getPost(postId: Int, userId: Int): Result<PostEntity>

    suspend fun postScoopPost(postId: Int, userId: Int): Result<Boolean>

    suspend fun postAddMap(postId: Int, userId: Int): Result<Boolean>

    suspend fun deletePinMap(postId: Int, userId: Int): Result<Boolean>
}
