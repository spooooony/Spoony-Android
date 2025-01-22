package com.spoony.spoony.data.datasource

import com.spoony.spoony.data.dto.base.BaseResponse
import com.spoony.spoony.data.dto.response.GetPostResponseDTO

interface PostRemoteDataSource {
    suspend fun getPostData(postId: Int, userId: Int): BaseResponse<GetPostResponseDTO>
}
