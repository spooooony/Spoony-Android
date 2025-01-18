package com.spoony.spoony.presentation.placeDetail

import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.domain.entity.IconTagEntity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.immutableListOf

data class PlaceDetailState(
    var iconTag: UiState<IconTagEntity> = UiState.Loading,
    var textTitle: String = "",
    var textContent: String = "",
    var profileUrl: String = "",
    var profileName: String = "",
    var profileLocation: String = "",
    var imageList: ImmutableList<String> = immutableListOf(),
    var dateString: String = "",
    var locationAddress: String = "",
    var locationName: String = "",
    var locationPinCount: Int = 0,
    var mySpoonCount: Int = 0,
    var menuItems: ImmutableList<String> = immutableListOf(),
    var isSpoonEat: Boolean = false,
    var isMyMap: Boolean = false
)
