package com.spoony.spoony.presentation.map

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
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
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.overlay.OverlayImage
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.bottomsheet.SpoonyAdvancedBottomSheet
import com.spoony.spoony.core.designsystem.component.topappbar.CloseTopAppBar
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.type.AdvancedSheetState
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.core.util.extension.hexToColor
import com.spoony.spoony.core.util.extension.toValidHexColor
import com.spoony.spoony.domain.entity.AddedMapPostEntity
import com.spoony.spoony.domain.entity.AddedPlaceEntity
import com.spoony.spoony.presentation.map.component.MapPlaceDetailCard
import com.spoony.spoony.presentation.map.component.bottomsheet.MapBottomSheetDragHandle
import com.spoony.spoony.presentation.map.component.bottomsheet.MapEmptyBottomSheetContent
import com.spoony.spoony.presentation.map.component.bottomsheet.MapListItem
import com.spoony.spoony.presentation.map.locationMap.LocationMapViewModel
import com.spoony.spoony.presentation.map.model.LocationModel
import io.morfly.compose.bottomsheet.material3.rememberBottomSheetScaffoldState
import io.morfly.compose.bottomsheet.material3.rememberBottomSheetState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Composable
fun LocationMapRoute(
    paddingValues: PaddingValues,
    navigateToPlaceDetail: (Int) -> Unit,
    navigateToExplore: () -> Unit,
    navigateUp: () -> Unit,
    viewModel: LocationMapViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val userName = when (state.userName) {
        is UiState.Success -> {
            (state.userName as UiState.Success<String>).data
        }

        else -> ""
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(
            LatLng(
                state.locationModel.latitude,
                state.locationModel.longitude
            ),
            state.locationModel.scale
        )
    }

    val addedPlaceList =
        if (state.addedPlaceList is UiState.Success) {
            (state.addedPlaceList as UiState.Success<ImmutableList<AddedPlaceEntity>>).data
        } else {
            persistentListOf()
        }

    LocationMapScreen(
        paddingValues = paddingValues,
        cameraPositionState = cameraPositionState,
        userName = userName,
        placeCount = state.placeCount,
        placeList = addedPlaceList,
        placeCardList = state.placeCardInfo,
        locationInfo = state.locationModel,
        onExploreButtonClick = navigateToExplore,
        onPlaceItemClick = viewModel::getPlaceInfo,
        onPlaceCardClick = navigateToPlaceDetail,
        onCloseButtonClick = navigateUp
    )
}

@OptIn(ExperimentalNaverMapApi::class, ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun LocationMapScreen(
    paddingValues: PaddingValues,
    cameraPositionState: CameraPositionState,
    userName: String,
    placeCount: Int,
    locationInfo: LocationModel,
    placeList: ImmutableList<AddedPlaceEntity>,
    placeCardList: UiState<ImmutableList<AddedMapPostEntity>>,
    onExploreButtonClick: () -> Unit,
    onPlaceItemClick: (Int) -> Unit,
    onPlaceCardClick: (Int) -> Unit,
    onCloseButtonClick: () -> Unit
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
    var selectedMarkerId by remember { mutableIntStateOf(-1) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        NaverMap(
            cameraPositionState = cameraPositionState,
            onMapClick = { _, _ ->
                if (isSelected) {
                    selectedMarkerId = -1
                    isSelected = false
                }
            }
        ) {
            placeList.forEach { place ->
                key(place.placeId) {
                    Marker(
                        state = MarkerState(LatLng(place.latitude, place.longitude)),
                        captionText = place.placeName,
                        captionColor = SpoonyAndroidTheme.colors.black,
                        captionHaloColor = SpoonyAndroidTheme.colors.white,
                        captionRequestedWidth = 10.dp,
                        captionOffset = (-15).dp,
                        iconTintColor = Color.Unspecified,
                        icon = OverlayImage.fromResource(
                            if (selectedMarkerId == place.placeId) {
                                R.drawable.ic_selected_marker
                            } else {
                                R.drawable.ic_unselected_marker
                            }
                        ),
                        onClick = {
                            selectedMarkerId = if (selectedMarkerId == place.placeId) -1 else place.placeId
                            onPlaceItemClick(place.placeId)
                            isSelected = selectedMarkerId == place.placeId
                            cameraPositionState.move(
                                CameraUpdate.scrollTo(
                                    LatLng(place.latitude, place.longitude)
                                ).animate(CameraAnimation.Easing)
                            )
                            true
                        }
                    )
                }
            }
        }

        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = paddingValues.calculateBottomPadding())
                .padding(vertical = 5.dp, horizontal = 26.dp),
            visible = isSelected,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOut(targetOffset = { IntOffset(0, it.height) })
        ) {
            if (placeCardList is UiState.Success) {
                val pagerState = rememberPagerState(
                    initialPage = 0,
                    pageCount = { placeCardList.data.size }
                )

                HorizontalPager(
                    state = pagerState,
                    beyondViewportPageCount = 1
                ) { currentPage ->
                    val pageIndex = currentPage % placeCardList.data.size

                    with(placeCardList.data[pageIndex]) {
                        MapPlaceDetailCard(
                            placeName = placeName,
                            review = postTitle,
                            imageUrlList = photoUrlList.take(3).toImmutableList(),
                            categoryIconUrl = categoryEntity.iconUrl,
                            categoryName = categoryEntity.categoryName,
                            textColor = Color.hexToColor(categoryEntity.textColor.toValidHexColor()),
                            backgroundColor = Color.hexToColor(categoryEntity.backgroundColor.toValidHexColor()),
                            onClick = { onPlaceCardClick(postId) },
                            username = authorName,
                            placeSpoon = authorRegionName,
                            addMapCount = zzimCount
                        )
                    }
                }
            }
        }

        Column {
            CloseTopAppBar(
                title = locationInfo.placeName ?: "",
                onCloseButtonClick = onCloseButtonClick
            )
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
                                name = userName,
                                resultCount = placeCount
                            )
                        }
                    },
                    sheetContent = {
                        if (placeList.isEmpty()) {
                            MapEmptyBottomSheetContent(
                                onClick = onExploreButtonClick,
                                modifier = Modifier
                                    .padding(bottom = paddingValues.calculateBottomPadding())
                            )
                        } else {
                            LazyColumn(
                                contentPadding = PaddingValues(bottom = paddingValues.calculateBottomPadding()),
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
                                            textColor = Color.hexToColor(categoryInfo.textColor.toValidHexColor()),
                                            backgroundColor = Color.hexToColor(categoryInfo.backgroundColor.toValidHexColor()),
                                            onClick = {
                                                onPlaceItemClick(placeId)
                                                cameraPositionState.move(
                                                    CameraUpdate.scrollTo(
                                                        LatLng(addedPlace.latitude, addedPlace.longitude)
                                                    ).animate(CameraAnimation.Easing)
                                                )
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
}
