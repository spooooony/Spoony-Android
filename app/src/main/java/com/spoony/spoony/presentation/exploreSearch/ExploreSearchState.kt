package com.spoony.spoony.presentation.exploreSearch

import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.domain.entity.CategoryEntity
import com.spoony.spoony.presentation.exploreSearch.type.SearchType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class ExploreSearchState(
    val searchKeyword: String = "",
    val recentReviewSearchQueryList: ImmutableList<String> = persistentListOf(),
    val recentUserSearchQueryList: ImmutableList<String> = persistentListOf(),
    val searchType: SearchType = SearchType.USER,
    val userInfoList: UiState<ImmutableList<UserInfo>> = UiState.Loading,
    val placeReviewInfoList: UiState<ImmutableList<PlaceReviewInfo>> = UiState.Loading
)

data class UserInfo(
    val userId: Int = 0,
    val imageUrl: String = "",
    val nickname: String = "",
    val region: String = ""
)

data class PlaceReviewInfo(
    val reviewId: Int = 0,
    val userId: Int = 0,
    val userName: String? = null,
    val userRegion: String? = null,
    val description: String,
    val photoUrlList: ImmutableList<String>? = persistentListOf(),
    val placeName: String? = null,
    val placeAddress: String? = null,
    val category: CategoryEntity? = null,
    val addMapCount: Int? = null,
    val createdAt: String? = null
)
