package com.spoony.spoony.presentation.map

import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.domain.entity.AddedPlaceEntity
import com.spoony.spoony.presentation.map.model.LocationModel
import kotlinx.collections.immutable.ImmutableList

data class MapState(
    val locationModel: LocationModel = LocationModel(
        latitude = 37.554524,
        longitude = 126.926447
    ),
    val addedPlaceList: UiState<ImmutableList<AddedPlaceEntity>> = UiState.Loading
)
