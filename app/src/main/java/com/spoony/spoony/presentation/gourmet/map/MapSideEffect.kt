package com.spoony.spoony.presentation.gourmet.map

sealed class MapSideEffect {
    data class ShowSnackBar(val message: String) : MapSideEffect()
}
