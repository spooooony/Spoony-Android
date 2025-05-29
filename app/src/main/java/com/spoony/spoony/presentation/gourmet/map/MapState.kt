package com.spoony.spoony.presentation.gourmet.map

import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.presentation.gourmet.map.model.LocationModel
import com.spoony.spoony.presentation.gourmet.map.model.PlaceReviewModel
import com.spoony.spoony.presentation.gourmet.map.model.ReviewCardModel
import kotlinx.collections.immutable.ImmutableList

data class MapState(
    val userName: UiState<String> = UiState.Loading,
    val locationModel: LocationModel = LocationModel(
        latitude = 37.554524,
        longitude = 126.926447
    ),
    val placeCardInfo: UiState<ImmutableList<ReviewCardModel>> = UiState.Loading,
    val addedPlaceList: UiState<ImmutableList<PlaceReviewModel>> = UiState.Loading,
    val placeCount: Int = 0
)
