package com.spoony.spoony.presentation.explore

import com.spoony.spoony.presentation.register.model.RegisterType
import com.spoony.spoony.presentation.report.ReportType

sealed class ExploreSideEffect {
    data class ShowSnackbar(val message: String) : ExploreSideEffect()
    data object ScrollToTop : ExploreSideEffect()
    data object NavigateToSearch : ExploreSideEffect()
    data object NavigateToRegister : ExploreSideEffect()
    data class NavigateToPlaceDetail(val id: Int) : ExploreSideEffect()
    data class NavigateToEdit(val id: Int, val type: RegisterType) : ExploreSideEffect()
    data class NavigateToReport(val targetId: Int, val type: ReportType) : ExploreSideEffect()
}
