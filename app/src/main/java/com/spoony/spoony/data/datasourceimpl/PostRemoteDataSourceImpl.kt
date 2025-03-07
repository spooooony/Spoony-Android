package com.spoony.spoony.data.datasourceimpl

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.datasource.PostRemoteDataSource
import com.spoony.spoony.data.dto.request.AddMapRequestDto
import com.spoony.spoony.data.dto.request.PostScoopRequestDto
import com.spoony.spoony.data.dto.response.AddedMapListResponseDto
import com.spoony.spoony.data.dto.response.AddedMapPostListDto
import com.spoony.spoony.data.dto.response.GetPostResponseDto
import com.spoony.spoony.data.dto.response.ZzimLocationResponseDto
import com.spoony.spoony.data.service.PostService
import javax.inject.Inject

class PostRemoteDataSourceImpl @Inject constructor(
    private val postService: PostService
) : PostRemoteDataSource {
    override suspend fun getPostData(postId: Int): BaseResponse<GetPostResponseDto> =
        postService.getPost(postId = postId)

    override suspend fun postScoopPost(postId: Int): BaseResponse<Boolean> =
        postService.postScoopPost(
            PostScoopRequestDto(postId = postId)
        )

    override suspend fun deletePinMap(postId: Int): BaseResponse<Boolean> =
        postService.deletePinMap(
            postId = postId
        )

    override suspend fun getAddedMapPost(postId: Int): BaseResponse<AddedMapPostListDto> =
        postService.getAddedMapPost(
            postId = postId
        )

    override suspend fun getAddedMap(): BaseResponse<AddedMapListResponseDto> =
        postService.getAddedMap()

    override suspend fun postAddMapData(postId: Int): BaseResponse<Boolean> =
        postService.postAddMapPost(
            AddMapRequestDto(postId = postId)
        )

    override suspend fun getZzimByLocation(locationId: Int): BaseResponse<ZzimLocationResponseDto> =
        postService.getZzimByLocation(locationId)
}
