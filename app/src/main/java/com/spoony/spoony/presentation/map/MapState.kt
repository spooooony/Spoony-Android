package com.spoony.spoony.presentation.map

import com.spoony.spoony.presentation.map.model.LocationModel

data class MapState(
    val locationModel: LocationModel = LocationModel(
        latitude = 37.554524,
        longitude = 126.926447
    )
)
