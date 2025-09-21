package com.spoony.spoony.presentation.exploreSearch

import com.spoony.spoony.presentation.register.model.RegisterType
import com.spoony.spoony.presentation.report.ReportType

sealed class ExploreSearchSideEffect {
    data class ShowSnackBar(val message: String) : ExploreSearchSideEffect()
    data class NavigateToUserProfile(val userId: Int) : ExploreSearchSideEffect()
    data object NavigateToMyPage : ExploreSearchSideEffect()
    data class NavigateToPlaceDetail(val placeId: Int) : ExploreSearchSideEffect()
    data object NavigateBack : ExploreSearchSideEffect()
    data class NavigateToReport(val targetId: Int, val type: ReportType) : ExploreSearchSideEffect()
    data class NavigateToEdit(val reviewId: Int, val type: RegisterType) : ExploreSearchSideEffect()
}
