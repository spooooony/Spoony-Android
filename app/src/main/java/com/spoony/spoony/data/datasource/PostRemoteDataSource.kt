package com.spoony.spoony.data.datasource

import com.spoony.spoony.data.dto.base.BaseResponseDTO

interface PostRemoteDataSource {
    suspend fun postScoopPost(postId: Int, userId: Int): BaseResponseDTO<Boolean>
}
