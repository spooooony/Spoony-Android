package com.spoony.spoony.presentation.placeDetail

sealed class PlaceDetailSideEffect {
    data object NavigateUp : PlaceDetailSideEffect()
    data class ShowSnackbar(val message: String) : PlaceDetailSideEffect()
}
