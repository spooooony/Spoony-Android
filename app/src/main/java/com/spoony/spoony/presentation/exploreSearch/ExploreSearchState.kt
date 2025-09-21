package com.spoony.spoony.presentation.exploreSearch

import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.presentation.exploreSearch.model.ExploreSearchPlaceReviewModel
import com.spoony.spoony.presentation.exploreSearch.model.ExploreSearchUserModel
import com.spoony.spoony.presentation.exploreSearch.type.SearchType
import com.spoony.spoony.presentation.register.model.RegisterType
import com.spoony.spoony.presentation.report.ReportType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class ExploreSearchState(
    val searchKeyword: String = "",
    val recentReviewSearchQueryList: ImmutableList<String> = persistentListOf(),
    val recentUserSearchQueryList: ImmutableList<String> = persistentListOf(),
    val searchType: SearchType = SearchType.USER,
    val userInfoList: UiState<ImmutableList<ExploreSearchUserModel>> = UiState.Loading,
    val placeReviewInfoList: UiState<ImmutableList<ExploreSearchPlaceReviewModel>> = UiState.Loading
)

sealed interface ExploreSearchAction {
    data class ClickReviewReport(val targetId: Int, val type: ReportType) : ExploreSearchAction
    data class ClickUser(val userId: Int) : ExploreSearchAction
    data object ClickMyPage : ExploreSearchAction
    data class ClickPlaceDetail(val placeId: Int) : ExploreSearchAction
    data object ClickBack : ExploreSearchAction
    data class RemoveRecentSearch(val keyword: String) : ExploreSearchAction
    data class SwitchType(val type: SearchType) : ExploreSearchAction
    data object ClearRecentSearch : ExploreSearchAction
    data class Search(val keyword: String) : ExploreSearchAction
    data class ClickEditReview(val reviewId: Int, val type: RegisterType) : ExploreSearchAction
    data object ClearSearchKeyword : ExploreSearchAction
    data class DeleteReview(val reviewId: Int) : ExploreSearchAction
}
