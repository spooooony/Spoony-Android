package com.spoony.spoony.data.datasourceimpl

import com.spoony.spoony.data.datasource.PostRemoteDataSource
import com.spoony.spoony.data.dto.base.BaseResponse
import com.spoony.spoony.data.dto.response.GetPostResponseDto
import com.spoony.spoony.data.service.PostService
import javax.inject.Inject

class PostRemoteDataSourceImpl @Inject constructor(
    private val postService: PostService
) : PostRemoteDataSource {
    override suspend fun getPostData(postId: Int, userId: Int): BaseResponse<GetPostResponseDto> = postService.getPost(userId = userId, postId = postId)
}
