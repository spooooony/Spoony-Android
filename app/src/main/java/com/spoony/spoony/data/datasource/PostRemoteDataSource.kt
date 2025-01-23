package com.spoony.spoony.data.datasource

import com.spoony.spoony.data.dto.base.BaseResponse
import com.spoony.spoony.data.dto.response.AddedMapListResponseDto
import com.spoony.spoony.data.dto.response.AddedMapPostListDto
import com.spoony.spoony.data.dto.response.GetPostResponseDto

interface PostRemoteDataSource {
    suspend fun postScoopPost(postId: Int, userId: Int): BaseResponse<Boolean>
    suspend fun getPostData(postId: Int, userId: Int): BaseResponse<GetPostResponseDto>
    suspend fun postAddMapData(postId: Int, userId: Int): BaseResponse<Boolean>
    suspend fun deletePinMap(postId: Int, userId: Int): BaseResponse<Boolean>
    suspend fun getAddedMapPost(postId: Int, userId: Int): BaseResponse<AddedMapPostListDto>
    suspend fun getAddedMap(userId: Int): BaseResponse<AddedMapListResponseDto>
}
