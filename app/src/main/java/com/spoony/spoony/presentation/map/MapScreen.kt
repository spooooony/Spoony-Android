package com.spoony.spoony.presentation.map

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
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
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.core.util.extension.hexToColor
import com.spoony.spoony.domain.entity.AddedPlaceEntity
import com.spoony.spoony.presentation.map.component.MapPlaceDetailCard
import com.spoony.spoony.presentation.map.component.bottomsheet.MapBottomSheetDragHandle
import com.spoony.spoony.presentation.map.component.bottomsheet.MapEmptyBottomSheetContent
import com.spoony.spoony.presentation.map.component.bottomsheet.MapListItem
import io.morfly.compose.bottomsheet.material3.rememberBottomSheetScaffoldState
import io.morfly.compose.bottomsheet.material3.rememberBottomSheetState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

private const val DEFAULT_ZOOM = 14.0

@Composable
fun MapRoute(
    paddingValues: PaddingValues,
    viewModel: MapViewModel = hiltViewModel(),
    navigateToMapSearch: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(
            LatLng(
                state.locationModel.latitude,
                state.locationModel.longitude
            ),
            DEFAULT_ZOOM
        )
    }

    when (state.addedPlaceList) {
        is UiState.Success -> {
            MapScreen(
                paddingValues = paddingValues,
                cameraPositionState = cameraPositionState,
                placeList = (state.addedPlaceList as UiState.Success<ImmutableList<AddedPlaceEntity>>).data,
                onPlaceCardClick = navigateToMapSearch
            )
        }

        else -> {}
    }
}

@OptIn(ExperimentalNaverMapApi::class, ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MapScreen(
    paddingValues: PaddingValues,
    cameraPositionState: CameraPositionState,
    placeList: ImmutableList<AddedPlaceEntity>,
    onPlaceCardClick: () -> Unit
) {
    val sheetState = rememberBottomSheetState(
        initialValue = AdvancedSheetState.PartiallyExpanded,
        defineValues = {
            AdvancedSheetState.Collapsed at height(20)
            AdvancedSheetState.PartiallyExpanded at height(50)
            AdvancedSheetState.Expanded at height(95)
        },
        confirmValueChange = { true }
    )
    val scaffoldState = rememberBottomSheetScaffoldState(sheetState)

    var isSelected by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        NaverMap(
            cameraPositionState = cameraPositionState,
            onMapClick = { _, _ ->
                if (isSelected) {
                    isSelected = false
                }
            }
        )

        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = paddingValues.calculateBottomPadding())
                .padding(vertical = 5.dp, horizontal = 26.dp),
            visible = isSelected,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOut(targetOffset = { IntOffset(0, it.height) })
        ) {
            MapPlaceDetailCard(
                placeName = "파오리",
                review = "리뷰에옹",
                imageUrlList = persistentListOf(
                    "https://github.com/Morfly/advanced-bottomsheet-compose/raw/main/demos/demo_cover.png",
                    "https://github.com/Morfly/advanced-bottomsheet-compose/raw/main/demos/demo_cover.png",
                    "https://github.com/Morfly/advanced-bottomsheet-compose/raw/main/demos/demo_cover.png"
                ),
                categoryIconUrl = "https://github.com/Morfly/advanced-bottomsheet-compose/raw/main/demos/demo_cover.png",
                categoryName = "주류",
                textColor = SpoonyAndroidTheme.colors.white,
                backgroundColor = SpoonyAndroidTheme.colors.white,
                onClick = onPlaceCardClick,
                username = "효비",
                placeSpoon = "성동구",
                addMapCount = 21
            )
        }

        AnimatedVisibility(
            visible = !isSelected,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOut(targetOffset = { IntOffset(0, it.height) })
        ) {
            SpoonyAdvancedBottomSheet(
                sheetState = scaffoldState,
                dragHandle = {
                    if (placeList.isNotEmpty()) {
                        MapBottomSheetDragHandle(
                            "효비",
                            5
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
                                .fillMaxSize()
                                .padding(bottom = paddingValues.calculateBottomPadding())
                        ) {
                            items(
                                items = placeList,
                                key = { it.placeId }
                            ) { addedPlace ->
                                with(addedPlace) {
                                    MapListItem(
                                        placeName = placeName,
                                        address = placeAddress,
                                        review = postTitle,
                                        imageUrl = photoUrl,
                                        categoryIconUrl = categoryInfo.iconUrl,
                                        categoryName = categoryInfo.categoryName,
                                        textColor = Color.hexToColor(categoryInfo.textColor ?: "000000"),
                                        backgroundColor = Color.hexToColor(categoryInfo.backgroundColor ?: "000000"),
                                        onClick = {
                                            isSelected = true
                                        }
                                    )
                                }
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
            ) {}
        }
    }
}
