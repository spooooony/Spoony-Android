package com.spoony.spoony.presentation.map

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.overlay.OverlayImage
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.bottomsheet.SpoonyAdvancedBottomSheet
import com.spoony.spoony.core.designsystem.component.tag.LogoTag
import com.spoony.spoony.core.designsystem.component.topappbar.TitleTopAppBar
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.type.AdvancedSheetState
import com.spoony.spoony.core.designsystem.type.TagSize
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.core.util.extension.hexToColor
import com.spoony.spoony.core.util.extension.noRippleClickable
import com.spoony.spoony.core.util.extension.toValidHexColor
import com.spoony.spoony.domain.entity.AddedMapPostEntity
import com.spoony.spoony.domain.entity.AddedPlaceEntity
import com.spoony.spoony.presentation.map.component.MapPlaceDetailCard
import com.spoony.spoony.presentation.map.component.bottomsheet.MapBottomSheetDragHandle
import com.spoony.spoony.presentation.map.component.bottomsheet.MapEmptyBottomSheetContent
import com.spoony.spoony.presentation.map.component.bottomsheet.MapListItem
import com.spoony.spoony.presentation.map.model.LocationModel
import io.morfly.compose.bottomsheet.material3.rememberBottomSheetScaffoldState
import io.morfly.compose.bottomsheet.material3.rememberBottomSheetState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun MapRoute(
    paddingValues: PaddingValues,
    viewModel: MapViewModel = hiltViewModel(),
    navigateToPlaceDetail: (Int) -> Unit,
    navigateToMapSearch: () -> Unit,
    navigateUp: () -> Unit
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

    when (state.addedPlaceList) {
        is UiState.Success -> {
            MapScreen(
                paddingValues = paddingValues,
                cameraPositionState = cameraPositionState,
                userName = userName,
                placeCount = state.placeCount,
                spoonCount = state.spoonCount,
                placeList = (state.addedPlaceList as UiState.Success<ImmutableList<AddedPlaceEntity>>).data,
                placeCardList = state.placeCardInfo,
                locationInfo = state.locationModel,
                onPlaceItemClick = viewModel::getPlaceInfo,
                onPlaceCardClick = navigateToPlaceDetail,
                navigateToMapSearch = navigateToMapSearch,
                onBackButtonClick = navigateUp
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
    userName: String,
    placeCount: Int,
    spoonCount: Int,
    locationInfo: LocationModel,
    placeList: ImmutableList<AddedPlaceEntity>,
    placeCardList: UiState<ImmutableList<AddedMapPostEntity>>,
    onPlaceItemClick: (Int) -> Unit,
    onPlaceCardClick: (Int) -> Unit,
    navigateToMapSearch: () -> Unit,
    onBackButtonClick: () -> Unit
) {
    val sheetState = rememberBottomSheetState(
        initialValue = AdvancedSheetState.PartiallyExpanded,
        defineValues = {
            AdvancedSheetState.Collapsed at height(20)
            AdvancedSheetState.PartiallyExpanded at height(50)
            AdvancedSheetState.Expanded at height(90)
        },
        confirmValueChange = { true }
    )
    val scaffoldState = rememberBottomSheetScaffoldState(sheetState)

    var isSelected by remember { mutableStateOf(false) }
    var selectedMarkerId by remember { mutableStateOf(-1) }

    Box(modifier = Modifier.fillMaxSize()) {
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
                        iconTintColor = Color.Unspecified,
                        icon = OverlayImage.fromResource(
                            if (selectedMarkerId == place.placeId) {
                                R.drawable.ic_selected_marker
                            } else R.drawable.ic_unselected_marker
                        ),
                        onClick = {
                            selectedMarkerId = if (selectedMarkerId == place.placeId) -1 else place.placeId
                            onPlaceItemClick(place.placeId)
                            isSelected = selectedMarkerId == place.placeId
                            true
                        }
                    )
                }
            }
        }

        if (locationInfo.placeId == null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(top = paddingValues.calculateTopPadding())
                    .padding(vertical = 6.dp, horizontal = 20.dp)
            ) {
                LogoTag(
                    count = spoonCount,
                    tagSize = TagSize.Large,
                    modifier = Modifier
                        .padding(end = 11.dp)
                )
                Row(
                    modifier = Modifier
                        .noRippleClickable(onClick = navigateToMapSearch)
                        .background(
                            color = SpoonyAndroidTheme.colors.white,
                            RoundedCornerShape(10.dp)
                        )
                        .border(
                            BorderStroke(1.dp, SpoonyAndroidTheme.colors.gray100),
                            RoundedCornerShape(10.dp)
                        )
                        .padding(vertical = 12.dp, horizontal = 16.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_search_24),
                        contentDescription = null,
                        tint = SpoonyAndroidTheme.colors.gray600,
                        modifier = Modifier
                            .size(20.dp)
                    )
                    Text(
                        text = "오늘은 어디서 먹어볼까요?",
                        style = SpoonyAndroidTheme.typography.body2m,
                        color = SpoonyAndroidTheme.colors.gray500,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        } else {
            TitleTopAppBar(
                title = locationInfo.placeName ?: "",
                onBackButtonClick = onBackButtonClick
            )
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
                            imageUrlList = photoUrlList.toImmutableList(),
                            categoryIconUrl = categoryEntity.iconUrl,
                            categoryName = categoryEntity.categoryName,
                            textColor = Color.hexToColor(categoryEntity.textColor.toValidHexColor()),
                            backgroundColor = Color.hexToColor(categoryEntity.backgroundColor.toValidHexColor()),
                            onClick = { onPlaceCardClick(placeId) },
                            username = authorName,
                            placeSpoon = authorRegionName,
                            addMapCount = zzimCount
                        )
                    }
                }
            }
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
                            name = userName,
                            resultCount = placeCount
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
                                        textColor = Color.hexToColor(categoryInfo.textColor.toValidHexColor()),
                                        backgroundColor = Color.hexToColor(categoryInfo.backgroundColor.toValidHexColor()),
                                        onClick = {
                                            onPlaceItemClick(placeId)
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
