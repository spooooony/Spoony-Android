package com.spoony.spoony.presentation.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState

private const val DEFAULT_ZOOM = 14.0

@Composable
fun MapRoute(
    viewModel: MapViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(
            LatLng(
                state.placeModel.latitude,
                state.placeModel.longitude
            ),
            DEFAULT_ZOOM
        )
    }

    MapScreen(
        cameraPositionState = cameraPositionState
    )
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapScreen(
    cameraPositionState: CameraPositionState
) {
    NaverMap(
        cameraPositionState = cameraPositionState
    )
}
