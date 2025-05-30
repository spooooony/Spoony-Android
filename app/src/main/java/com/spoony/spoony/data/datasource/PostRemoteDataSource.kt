package com.spoony.spoony.data.datasource

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.dto.response.AddedMapListResponseDto
import com.spoony.spoony.data.dto.response.AddedMapPostListDto
import com.spoony.spoony.data.dto.response.GetPostResponseDto

interface PostRemoteDataSource {
    suspend fun postScoopPost(postId: Int): BaseResponse<Boolean>
    suspend fun getPostData(postId: Int): BaseResponse<GetPostResponseDto>
    suspend fun postAddMapData(postId: Int): BaseResponse<Boolean>
    suspend fun deletePinMap(postId: Int): BaseResponse<Boolean>
    suspend fun getAddedMapPost(postId: Int): BaseResponse<AddedMapPostListDto>
    suspend fun getAddedMap(categoryId: Int): BaseResponse<AddedMapListResponseDto>
    suspend fun getZzimByLocation(locationId: Int): BaseResponse<AddedMapListResponseDto>
    suspend fun deletePost(postId: Int): BaseResponse<Unit>
}
