package com.spoony.spoony.presentation.attendance

sealed class AttendanceSideEffect {
    data class ShowSnackBar(val message: String) : AttendanceSideEffect()
}
