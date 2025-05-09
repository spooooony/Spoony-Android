package com.spoony.spoony.presentation.placeDetail

import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.domain.entity.UserEntity
import com.spoony.spoony.presentation.placeDetail.model.PlaceDetailModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class PlaceDetailState(
    val reviewId: UiState<Int> = UiState.Loading,
    val isScooped: Boolean = false,
    val isAddMap: Boolean = false,
    val addMapCount: Int = 0,
    val placeDetailModel: UiState<PlaceDetailModel> = UiState.Loading,
    val userEntity: UiState<UserEntity> = UiState.Loading,
    val spoonCount: UiState<Int> = UiState.Loading,
    val dropDownMenuList: ImmutableList<String> = persistentListOf()
)
