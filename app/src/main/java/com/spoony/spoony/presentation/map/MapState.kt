package com.spoony.spoony.presentation.map

import com.spoony.spoony.presentation.map.model.PlaceModel

data class MapState(
    val placeModel: PlaceModel = PlaceModel(
        latitude = 37.554524,
        longitude = 126.926447
    )
)
