package com.spoony.spoony.data.service

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.dto.response.ExplorePlaceReviewFilteredResponseDto
import com.spoony.spoony.data.dto.response.ExplorePlaceReviewFollowingResponseDto
import com.spoony.spoony.data.dto.response.PlaceReviewListResponseDto
import com.spoony.spoony.data.dto.response.UserListSearchResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ExploreService {
    @GET("/api/v1/feed/filtered")
    suspend fun getPlaceReviewListFiltered(
        @Query("categoryIds") categoryIds: List<Int>?,
        @Query("regionIds") regionIds: List<Int>?,
        @Query("ageGroups") ageGroups: List<String>?,
        @Query("sortBy") sortBy: String?,
        @Query("cursor") cursor: String?,
        @Query("size") size: Int?
    ): BaseResponse<ExplorePlaceReviewFilteredResponseDto>

    @GET("/api/v1/post/search")
    suspend fun getPlaceReviewByKeyword(
        @Query("query") query: String
    ): BaseResponse<PlaceReviewListResponseDto>

    @GET("/api/v1/user/search")
    suspend fun getUserListByKeyword(
        @Query("query") query: String
    ): BaseResponse<UserListSearchResponseDto>

    @GET("/api/v1/feed/following")
    suspend fun getPlaceReviewListFollowing(): BaseResponse<ExplorePlaceReviewFollowingResponseDto>
}
