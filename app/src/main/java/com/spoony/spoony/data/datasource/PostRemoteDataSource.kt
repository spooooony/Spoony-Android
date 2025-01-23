package com.spoony.spoony.data.datasource

import com.spoony.spoony.data.dto.base.BaseResponse
import com.spoony.spoony.data.dto.response.GetPostResponseDto

interface PostRemoteDataSource {
    suspend fun postScoopPost(postId: Int, userId: Int): BaseResponse<Boolean>
    suspend fun getPostData(postId: Int, userId: Int): BaseResponse<GetPostResponseDto>
    suspend fun postAddMapData(postId: Int, userId: Int): BaseResponse<Boolean>
    suspend fun deletePinMap(postId: Int, userId: Int): BaseResponse<Boolean>
}
