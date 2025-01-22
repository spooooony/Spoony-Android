package com.spoony.spoony.data.datasource

import com.spoony.spoony.data.dto.base.BaseResponse

interface PostRemoteDataSource {
    suspend fun postScoopPost(postId: Int, userId: Int): BaseResponse<Boolean>
}
