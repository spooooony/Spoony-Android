package com.spoony.spoony.presentation.gourmet.map.navigaion

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.spoony.spoony.core.navigation.MainTabRoute
import com.spoony.spoony.presentation.gourmet.map.MapRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToMap(
    locationId: Int? = null,
    locationName: String? = null,
    scale: String? = null,
    latitude: String? = null,
    longitude: String? = null,
    navOptions: NavOptions? = null
) {
    navigate(
        Map(
            locationId = locationId,
            locationName = locationName,
            scale = scale,
            latitude = latitude,
            longitude = longitude
        ),
        navOptions
    )
}

fun NavGraphBuilder.mapNavGraph(
    paddingValues: PaddingValues,
    navigateToPlaceDetail: (Int) -> Unit,
    navigateToMapSearch: () -> Unit,
    navigateToExplore: () -> Unit,
    navigateToAttendance: () -> Unit
) {
    composable<Map> {
        MapRoute(
            paddingValues = paddingValues,
            navigateToPlaceDetail = navigateToPlaceDetail,
            navigateToMapSearch = navigateToMapSearch,
            navigateToExplore = navigateToExplore,
            navigateToAttendance = navigateToAttendance
        )
    }
}

@Serializable
data class Map(
    val locationId: Int? = null,
    val locationName: String? = null,
    val scale: String? = null,
    val latitude: String? = null,
    val longitude: String? = null
) : MainTabRoute
