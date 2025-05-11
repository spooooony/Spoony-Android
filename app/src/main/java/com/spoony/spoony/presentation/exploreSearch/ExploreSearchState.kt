package com.spoony.spoony.presentation.exploreSearch

import com.spoony.spoony.core.designsystem.model.ReviewCardCategory
import com.spoony.spoony.core.state.UiState
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
    val userId: Int,
    val imageUrl: String,
    val nickname: String,
    val region: String
)

data class PlaceReviewInfo(
    val reviewId: Int,
    val userId: Int,
    val userName: String,
    val userRegion: String,
    val description: String,
    val isMine: Boolean,
    val photoUrlList: ImmutableList<String>,
    val category: ReviewCardCategory,
    val addMapCount: Int,
    val date: String
)
