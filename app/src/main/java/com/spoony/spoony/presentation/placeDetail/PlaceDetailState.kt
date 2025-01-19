package com.spoony.spoony.presentation.placeDetail

import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.domain.entity.PostEntity
import com.spoony.spoony.domain.entity.UserEntity

data class PlaceDetailState(
    val postEntity: UiState<PostEntity> = UiState.Loading,
    val userEntity: UiState<UserEntity> = UiState.Loading,
    val spoonAmountEntity: UiState<Int> = UiState.Loading,
    val scoopDialogVisibility: Boolean = false
)
