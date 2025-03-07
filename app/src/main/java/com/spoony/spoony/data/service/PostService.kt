package com.spoony.spoony.data.service

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.dto.request.AddMapRequestDto
import com.spoony.spoony.data.dto.request.PostScoopRequestDto
import com.spoony.spoony.data.dto.response.AddedMapListResponseDto
import com.spoony.spoony.data.dto.response.AddedMapPostListDto
import com.spoony.spoony.data.dto.response.GetPostResponseDto
import com.spoony.spoony.data.dto.response.ZzimLocationResponseDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface PostService {
    @GET("/api/v1/post/{postId}")
    suspend fun getPost(
        @Path("postId") postId: Int
    ): BaseResponse<GetPostResponseDto>

    @GET("/api/v1/post/zzim/location/{locationId}")
    suspend fun getZzimByLocation(
        @Path("locationId") locationId: Int
    ): BaseResponse<ZzimLocationResponseDto>

    @POST("/api/v1/post/zzim")
    suspend fun postAddMapPost(
        @Body addMapRequestDto: AddMapRequestDto
    ): BaseResponse<Boolean>

    @POST("/api/v1/post/scoop")
    suspend fun postScoopPost(
        @Body postScoopRequestDto: PostScoopRequestDto
    ): BaseResponse<Boolean>

    @DELETE("/api/v1/post/zzim/{postId}")
    suspend fun deletePinMap(
        @Path("postId") postId: Int
    ): BaseResponse<Boolean>

    @GET("/api/v1/post/zzim")
    suspend fun getAddedMap(): BaseResponse<AddedMapListResponseDto>

    @GET("/api/v1/post/zzim/{postId}")
    suspend fun getAddedMapPost(
        @Path("postId") postId: Int
    ): BaseResponse<AddedMapPostListDto>

    @Multipart
    @POST("/api/v1/post")
    suspend fun registerPost(
        @Part("data") data: RequestBody,
        @Part photos: List<MultipartBody.Part>
    ): BaseResponse<Unit>
}
