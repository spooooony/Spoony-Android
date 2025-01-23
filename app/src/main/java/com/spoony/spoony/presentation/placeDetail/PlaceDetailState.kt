package com.spoony.spoony.presentation.placeDetail

import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.domain.entity.UserEntity
import com.spoony.spoony.presentation.placeDetail.model.PostModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class PlaceDetailState(
    val postId: UiState<Int> = UiState.Loading,
    val isScooped: Boolean = false,
    val isAddMap: Boolean = false,
    val addMapCount: Int = 0,
    val postModel: UiState<PostModel> = UiState.Loading,
    val userEntity: UiState<UserEntity> = UiState.Loading,
    val spoonCount: UiState<Int> = UiState.Loading,
    val dropDownMenuList: ImmutableList<String> = persistentListOf()
)
