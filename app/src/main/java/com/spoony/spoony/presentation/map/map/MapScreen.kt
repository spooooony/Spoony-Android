package com.spoony.spoony.presentation.map.map

import android.view.Gravity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.spoony.spoony.core.designsystem.component.bottomsheet.SpoonyAdvancedBottomSheet
import com.spoony.spoony.core.designsystem.component.bottomsheet.SpoonyBasicDragHandle
import com.spoony.spoony.core.designsystem.component.topappbar.CloseTopAppBar
import com.spoony.spoony.core.designsystem.type.AdvancedSheetState
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.core.util.extension.hexToColor
import com.spoony.spoony.core.util.extension.toValidHexColor
import com.spoony.spoony.domain.entity.AddedMapPostEntity
import com.spoony.spoony.domain.entity.AddedPlaceEntity
import com.spoony.spoony.presentation.map.map.component.MapPlaceDetailCard
import com.spoony.spoony.presentation.map.map.component.MapTopAppBar
import com.spoony.spoony.presentation.map.map.component.SpoonyMapMarker
import com.spoony.spoony.presentation.map.map.component.bottomsheet.MapBottomSheetDragHandle
import com.spoony.spoony.presentation.map.map.component.bottomsheet.MapEmptyBottomSheetContent
import com.spoony.spoony.presentation.map.map.component.bottomsheet.MapListItem
import com.spoony.spoony.presentation.map.map.model.LocationModel
import io.morfly.compose.bottomsheet.material3.rememberBottomSheetScaffoldState
import io.morfly.compose.bottomsheet.material3.rememberBottomSheetState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

private const val DEFAULT_ZOOM = 14.0

@Composable
fun MapRoute(
    paddingValues: PaddingValues,
    navigateToPlaceDetail: (Int) -> Unit,
    navigateToMapSearch: () -> Unit,
    navigateToExplore: () -> Unit,
    navigateUp: () -> Unit,
    viewModel: MapViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    with(state.locationModel) {
        LaunchedEffect(placeId) {
            if (placeId == null) {
                viewModel.getAddedPlaceList()
                viewModel.getSpoonCount()
            } else {
                viewModel.getAddedPlaceListByLocation(locationId = placeId)
            }
        }
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

    MapScreen(
        paddingValues = paddingValues,
        cameraPositionState = cameraPositionState,
        userName = (state.userName as? UiState.Success<String>)?.data ?: "",
        placeCount = state.placeCount,
        spoonCount = state.spoonCount,
        placeList = (state.addedPlaceList as? UiState.Success<ImmutableList<AddedPlaceEntity>>)?.data ?: persistentListOf(),
        placeCardList = (state.placeCardInfo as? UiState.Success<ImmutableList<AddedMapPostEntity>>)?.data ?: persistentListOf(),
        locationInfo = state.locationModel,
        onExploreButtonClick = navigateToExplore,
        onPlaceItemClick = viewModel::getPlaceInfo,
        onPlaceCardClick = navigateToPlaceDetail,
        navigateToMapSearch = navigateToMapSearch,
        onBackButtonClick = navigateUp
    )
}

@OptIn(ExperimentalNaverMapApi::class, ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun MapScreen(
    paddingValues: PaddingValues,
    cameraPositionState: CameraPositionState,
    userName: String,
    placeCount: Int,
    spoonCount: Int,
    locationInfo: LocationModel,
    placeList: ImmutableList<AddedPlaceEntity>,
    placeCardList: ImmutableList<AddedMapPostEntity>,
    onExploreButtonClick: () -> Unit,
    onPlaceItemClick: (Int) -> Unit,
    onPlaceCardClick: (Int) -> Unit,
    navigateToMapSearch: () -> Unit,
    onBackButtonClick: () -> Unit
) {
    val systemPaddingValues = WindowInsets.systemBars.asPaddingValues()
    val sheetState = rememberBottomSheetState(
        initialValue = AdvancedSheetState.PartiallyExpanded,
        defineValues = {
            AdvancedSheetState.Collapsed at height(85.dp + paddingValues.calculateBottomPadding() + systemPaddingValues.calculateBottomPadding())
            AdvancedSheetState.PartiallyExpanded at height(50)
            AdvancedSheetState.Expanded at height(90)
        }
    )
    val scaffoldState = rememberBottomSheetScaffoldState(sheetState)

    var isMarkerSelected by remember { mutableStateOf(false) }
    var selectedMarkerId by remember { mutableIntStateOf(-1) }

    NaverMap(
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(
            isZoomControlEnabled = false,
            logoGravity = Gravity.TOP or Gravity.START,
            logoMargin = PaddingValues(start = 20.dp, top = 80.dp)
        ),
        onMapClick = { _, _ ->
            if (isMarkerSelected) {
                selectedMarkerId = -1
                isMarkerSelected = false
            }
        }
    ) {
        placeList.forEach { place ->
            key(place.placeId) {
                SpoonyMapMarker(
                    review = place,
                    selectedMarkerId = selectedMarkerId,
                    onClick = {
                        if (selectedMarkerId == place.placeId) {
                            selectedMarkerId = -1
                        } else {
                            selectedMarkerId = place.placeId
                            onPlaceItemClick(place.placeId)
                            cameraPositionState.move(
                                CameraUpdate
                                    .scrollAndZoomTo(
                                        LatLng(place.latitude, place.longitude),
                                        DEFAULT_ZOOM
                                    )
                                    .animate(CameraAnimation.Easing)
                            )
                        }
                        isMarkerSelected = selectedMarkerId == place.placeId
                    }
                )
            }
        }
    }

    if (locationInfo.placeId == null) {
        MapTopAppBar(
            spoonCount = spoonCount,
            onSearchClick = navigateToMapSearch,
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
        )
    } else {
        CloseTopAppBar(
            title = locationInfo.placeName ?: "",
            onCloseButtonClick = onBackButtonClick
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = paddingValues.calculateBottomPadding())
                .padding(vertical = 5.dp, horizontal = 26.dp),
            visible = isMarkerSelected,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOut(targetOffset = { IntOffset(0, it.height) })
        ) {
            val pagerState = rememberPagerState(
                initialPage = 0,
                pageCount = { placeCardList.size }
            )

            HorizontalPager(
                state = pagerState,
                beyondViewportPageCount = 1
            ) { currentPage ->
                val pageIndex = currentPage % placeCardList.size

                with(placeCardList[pageIndex]) {
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

        AnimatedVisibility(
            visible = !isMarkerSelected,
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
                    } else {
                        SpoonyBasicDragHandle()
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
                            contentPadding = PaddingValues(
                                top = 6.dp,
                                bottom = paddingValues.calculateBottomPadding()
                            ),
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
                                            isMarkerSelected = true
                                            selectedMarkerId = placeId
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
