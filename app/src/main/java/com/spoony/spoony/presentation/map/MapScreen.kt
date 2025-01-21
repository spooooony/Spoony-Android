package com.spoony.spoony.presentation.map

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.spoony.spoony.core.designsystem.component.bottomsheet.SpoonyAdvancedBottomSheet
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.type.AdvancedSheetState
import com.spoony.spoony.presentation.map.component.bottomsheet.MapBottomSheetDragHandle
import com.spoony.spoony.presentation.map.component.bottomsheet.MapEmptyBottomSheetContent
import com.spoony.spoony.presentation.map.component.bottomsheet.MapListItem
import io.morfly.compose.bottomsheet.material3.rememberBottomSheetScaffoldState
import io.morfly.compose.bottomsheet.material3.rememberBottomSheetState

private const val DEFAULT_ZOOM = 14.0

@Composable
fun MapRoute(
    paddingValues: PaddingValues,
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
        paddingValues = paddingValues,
        cameraPositionState = cameraPositionState,
        listOf("fadaf", "fadaf", "fadaf", "fadaf", "fadaf", "fadaf", "fadaf")
    )
}

@OptIn(ExperimentalNaverMapApi::class, ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MapScreen(
    paddingValues: PaddingValues,
    cameraPositionState: CameraPositionState,
    placeList: List<String>
) {
    val sheetState = rememberBottomSheetState(
        initialValue = AdvancedSheetState.PartiallyExpanded,
        defineValues = {
            AdvancedSheetState.Hidden at height(0)
            AdvancedSheetState.Collapsed at height(20)
            AdvancedSheetState.PartiallyExpanded at height(50)
            AdvancedSheetState.Expanded at height(95)
        },
        confirmValueChange = { true }
    )
    val scaffoldState = rememberBottomSheetScaffoldState(sheetState)

    SpoonyAdvancedBottomSheet(
        sheetState = scaffoldState,
        dragHandle = {
            if (placeList.isNotEmpty()) {
                MapBottomSheetDragHandle(
                    "효비",
                    5,
                )
            }
        },
        sheetContent = {
            if (placeList.isEmpty()) {
                MapEmptyBottomSheetContent(
                    onClick = {},
                    modifier = Modifier
                        .padding(bottom = paddingValues.calculateBottomPadding())
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .padding(bottom = paddingValues.calculateBottomPadding())
                ) {
                    items(placeList.size) { index ->
                        MapListItem(
                            placeName = placeList[index],
                            address = placeList[index],
                            review = placeList[index],
                            imageUrl = "https://github.com/Morfly/advanced-bottomsheet-compose/raw/main/demos/demo_cover.png",
                            categoryIconUrl = "https://github.com/Morfly/advanced-bottomsheet-compose/raw/main/demos/demo_cover.png",
                            categoryName = "주류",
                            textColor = SpoonyAndroidTheme.colors.white,
                            backgroundColor = SpoonyAndroidTheme.colors.white
                        )
                    }
                    item {
                        Spacer(
                            modifier = Modifier
                                .fillMaxHeight()
                        )
                    }
                }
            }

        },
        sheetSwipeEnabled = placeList.isNotEmpty()
    ) {
        NaverMap(
            cameraPositionState = cameraPositionState
        )
    }
}
