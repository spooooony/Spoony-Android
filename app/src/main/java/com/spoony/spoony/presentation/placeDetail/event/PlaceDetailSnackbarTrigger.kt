package com.spoony.spoony.presentation.placeDetail.event

import androidx.compose.runtime.staticCompositionLocalOf

val PlaceDetailLocalSnackBarTrigger = staticCompositionLocalOf<(String) -> Unit> {
    error("No SnackBar provided")
}
