package com.spoony.spoony.presentation.attendance

import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.presentation.attendance.model.SpoonDrawModel
import kotlinx.collections.immutable.ImmutableList

data class AttendanceState(
    val weeklyStartDate: String = "",
    val spoonDrawList: UiState<ImmutableList<SpoonDrawModel>> = UiState.Loading
)
