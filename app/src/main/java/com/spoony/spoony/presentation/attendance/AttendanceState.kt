package com.spoony.spoony.presentation.attendance

import com.spoony.spoony.core.designsystem.model.SpoonDrawModel
import com.spoony.spoony.core.state.UiState
import kotlinx.collections.immutable.ImmutableList

data class AttendanceState(
    val weeklyStartDate: String = "",
    val totalSpoonCount: Int = 0,
    val spoonDrawList: UiState<ImmutableList<SpoonDrawModel>> = UiState.Loading
)
