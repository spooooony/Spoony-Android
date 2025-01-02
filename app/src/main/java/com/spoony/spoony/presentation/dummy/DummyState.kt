package com.spoony.spoony.presentation.dummy

import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.domain.entity.DummyEntity

data class DummyState(
    var user: UiState<DummyEntity> = UiState.Loading,
)
