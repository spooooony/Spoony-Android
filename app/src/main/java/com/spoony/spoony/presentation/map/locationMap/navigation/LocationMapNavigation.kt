package com.spoony.spoony.presentation.map.locationMap.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.spoony.spoony.core.navigation.Route
import com.spoony.spoony.presentation.map.locationMap.LocationMapRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToLocationMap(
    navOptions: NavOptions? = null,
    locationId: Int? = null,
    locationName: String? = null,
    scale: String? = null,
    latitude: String? = null,
    longitude: String? = null
) {
    navigate(
        LocationMap(
            locationId = locationId,
            locationName = locationName,
            scale = scale,
            latitude = latitude,
            longitude = longitude
        ), navOptions
    )
}

fun NavGraphBuilder.locationMapNavGraph(
    paddingValues: PaddingValues,
    navigateToPlaceDetail: (postId: Int) -> Unit,
    navigateToExplore: () -> Unit,
    navigateUp: () -> Unit
) {
    composable<LocationMap> {
        LocationMapRoute(
            paddingValues = paddingValues,
            navigateToPlaceDetail = navigateToPlaceDetail,
            navigateToExplore = navigateToExplore,
            navigateUp = navigateUp

        )
    }
}

@Serializable
data class LocationMap(
    val locationId: Int? = null,
    val locationName: String? = null,
    val scale: String? = null,
    val latitude: String? = null,
    val longitude: String? = null
) : Route
