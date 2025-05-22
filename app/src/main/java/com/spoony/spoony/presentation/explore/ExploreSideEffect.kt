package com.spoony.spoony.presentation.explore

sealed class ExploreSideEffect {
    data class ShowSnackbar(val message: String) : ExploreSideEffect()
}
