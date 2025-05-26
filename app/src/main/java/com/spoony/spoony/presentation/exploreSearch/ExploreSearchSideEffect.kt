package com.spoony.spoony.presentation.exploreSearch

sealed class ExploreSearchSideEffect {
    data class ShowSnackBar(val message: String) : ExploreSearchSideEffect()
}
