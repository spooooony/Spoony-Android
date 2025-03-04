package com.spoony.spoony.data.datasource

import com.spoony.spoony.data.dto.base.BaseResponse
import com.spoony.spoony.data.dto.response.AddedMapListResponseDto
import com.spoony.spoony.data.dto.response.AddedMapPostListDto
import com.spoony.spoony.data.dto.response.GetPostResponseDto
import com.spoony.spoony.data.dto.response.ZzimLocationResponseDto

interface PostRemoteDataSource {
    suspend fun postScoopPost(postId: Int): BaseResponse<Boolean>
    suspend fun getPostData(postId: Int): BaseResponse<GetPostResponseDto>
    suspend fun postAddMapData(postId: Int): BaseResponse<Boolean>
    suspend fun deletePinMap(postId: Int): BaseResponse<Boolean>
    suspend fun getAddedMapPost(postId: Int): BaseResponse<AddedMapPostListDto>
    suspend fun getAddedMap(): BaseResponse<AddedMapListResponseDto>
    suspend fun getZzimByLocation(locationId: Int): BaseResponse<ZzimLocationResponseDto>
}
