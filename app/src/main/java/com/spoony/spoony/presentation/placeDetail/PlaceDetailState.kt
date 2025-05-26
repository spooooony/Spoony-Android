package com.spoony.spoony.presentation.placeDetail

import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.presentation.placeDetail.model.PlaceDetailModel
import com.spoony.spoony.presentation.placeDetail.model.UserInfoModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class PlaceDetailState(
    val reviewId: UiState<Int> = UiState.Loading,
    val isScooped: Boolean = false,
    val isAddMap: Boolean = false,
    val addMapCount: Int = 0,
    val placeDetailModel: UiState<PlaceDetailModel> = UiState.Loading,
    val userInfo: UiState<UserInfoModel> = UiState.Loading,
    val isFollowing: Boolean = false,
    val spoonCount: UiState<Int> = UiState.Loading,
    val dropDownMenuList: ImmutableList<String> = persistentListOf()
)
