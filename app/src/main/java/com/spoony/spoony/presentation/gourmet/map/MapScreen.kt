package com.spoony.spoony.presentation.gourmet.map

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.provider.Settings
import android.view.Gravity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationSource
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.location.FusedLocationSource
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.bottomsheet.SpoonyAdvancedBottomSheet
import com.spoony.spoony.core.designsystem.component.bottomsheet.SpoonyBasicDragHandle
import com.spoony.spoony.core.designsystem.component.chip.IconChip
import com.spoony.spoony.core.designsystem.component.dialog.SpoonDrawDialog
import com.spoony.spoony.core.designsystem.component.topappbar.CloseTopAppBar
import com.spoony.spoony.core.designsystem.event.LocalSnackBarTrigger
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.theme.white
import com.spoony.spoony.core.designsystem.type.AdvancedSheetState
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.core.util.extension.hexToColor
import com.spoony.spoony.core.util.extension.noRippleClickable
import com.spoony.spoony.core.util.extension.toValidHexColor
import com.spoony.spoony.domain.entity.AddedMapPostEntity
import com.spoony.spoony.domain.entity.AddedPlaceEntity
import com.spoony.spoony.presentation.gourmet.map.DefaultHeight.COLLAPSED_HEIGHT
import com.spoony.spoony.presentation.gourmet.map.DefaultHeight.MIN_PARTIALLY_HEIGHT
import com.spoony.spoony.presentation.gourmet.map.DefaultHeight.dragHandleHeight
import com.spoony.spoony.presentation.gourmet.map.component.LocationPermissionDialog
import com.spoony.spoony.presentation.gourmet.map.component.MapPlaceDetailCard
import com.spoony.spoony.presentation.gourmet.map.component.MapTopAppBar
import com.spoony.spoony.presentation.gourmet.map.component.SpoonyMapMarker
import com.spoony.spoony.presentation.gourmet.map.component.bottomsheet.MapBottomSheetDragHandle
import com.spoony.spoony.presentation.gourmet.map.component.bottomsheet.MapEmptyBottomSheetContent
import com.spoony.spoony.presentation.gourmet.map.component.bottomsheet.MapListItem
import com.spoony.spoony.presentation.gourmet.map.model.LocationModel
import io.morfly.compose.bottomsheet.material3.BottomSheetState
import io.morfly.compose.bottomsheet.material3.rememberBottomSheetScaffoldState
import io.morfly.compose.bottomsheet.material3.rememberBottomSheetState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

private const val DEFAULT_ZOOM = 14.0
private val LOCATION_PERMISSIONS = arrayOf(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION
)

@Composable
fun MapRoute(
    paddingValues: PaddingValues,
    navigateToPlaceDetail: (Int) -> Unit,
    navigateToMapSearch: () -> Unit,
    navigateToExplore: () -> Unit,
    navigateToAttendance: () -> Unit,
    navigateUp: () -> Unit,
    viewModel: MapViewModel = hiltViewModel()
) {
    val systemUiController = rememberSystemUiController()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val showSpoonDraw by viewModel.showSpoonDraw.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val showSnackBar = LocalSnackBarTrigger.current

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(
            LatLng(
                state.locationModel.latitude,
                state.locationModel.longitude
            ),
            state.locationModel.scale
        )
    }

    var shouldShowSystemDialog by remember {
        mutableStateOf(
            LOCATION_PERMISSIONS.all { permission ->
                ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, permission)
            }
        )
    }

    val fusedLocationProviderClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    var isPermissionDialogVisible by remember { mutableStateOf(false) }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) {
        shouldShowSystemDialog = LOCATION_PERMISSIONS.all { permission ->
            ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, permission)
        }

        getLastLocation(fusedLocationProviderClient) { location ->
            moveCamera(
                cameraPositionState = cameraPositionState,
                latLng = LatLng(location.latitude, location.longitude)
            )
        }
    }

    SideEffect {
        systemUiController.setNavigationBarColor(
            color = white
        )
    }

    with(state.locationModel) {
        LaunchedEffect(placeId) {
            if (placeId == null) {
                viewModel.getAddedPlaceList()
            } else {
                viewModel.getAddedPlaceListByLocation(locationId = placeId)
            }
        }
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is MapSideEffect.ShowSnackBar -> showSnackBar(sideEffect.message)
                }
            }
    }

    LaunchedEffect(Unit) {
        if (
            LOCATION_PERMISSIONS.any { permission ->
                ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
            }
        ) {
            getLastLocation(fusedLocationProviderClient) { location ->
                moveCamera(
                    cameraPositionState = cameraPositionState,
                    latLng = LatLng(location.latitude, location.longitude)
                )
            }
        }
    }

    CalculateDefaultHeight()

    if (isPermissionDialogVisible) {
        LocationPermissionDialog(
            onDismiss = { isPermissionDialogVisible = false },
            onPositiveButtonClick = {
                isPermissionDialogVisible = false
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.parse("package:" + context.packageName)
                    addCategory(Intent.CATEGORY_DEFAULT)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK

                    context.startActivity(this)
                }
            },
            onNegativeButtonClick = { isPermissionDialogVisible = false }
        )
    }

    MapScreen(
        paddingValues = paddingValues,
        cameraPositionState = cameraPositionState,
        userName = (state.userName as? UiState.Success<String>)?.data ?: "",
        placeCount = state.placeCount,
        placeList = (state.addedPlaceList as? UiState.Success<ImmutableList<AddedPlaceEntity>>)?.data ?: persistentListOf(),
        placeCardList = (state.placeCardInfo as? UiState.Success<ImmutableList<AddedMapPostEntity>>)?.data ?: persistentListOf(),
        locationInfo = state.locationModel,
        onExploreButtonClick = navigateToExplore,
        onPlaceItemClick = viewModel::getPlaceInfo,
        onPlaceCardClick = navigateToPlaceDetail,
        navigateToMapSearch = navigateToMapSearch,
        onBackButtonClick = navigateUp,
        moveCamera = { latitude, longitude ->
            moveCamera(
                cameraPositionState = cameraPositionState,
                latLng = LatLng(latitude, longitude)
            )
        },
        onGpsButtonClick = {
            if (
                LOCATION_PERMISSIONS.any { permission ->
                    ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
                }
            ) {
                getLastLocation(fusedLocationProviderClient) { location ->
                    moveCamera(
                        cameraPositionState = cameraPositionState,
                        latLng = LatLng(location.latitude, location.longitude)
                    )
                }
            } else {
                if (!shouldShowSystemDialog) {
                    isPermissionDialogVisible = true
                }

                locationPermissionLauncher.launch(LOCATION_PERMISSIONS)
            }
        }
    )

    if (showSpoonDraw) {
        viewModel.updateLastEntryDate()

        SpoonDrawDialog(
            onDismiss = viewModel::checkSpoonDrawn,
            onSpoonDrawButtonClick = viewModel::drawSpoon,
            onConfirmButtonClick = navigateToAttendance
        )
    }
}

@OptIn(ExperimentalNaverMapApi::class, ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun MapScreen(
    paddingValues: PaddingValues,
    cameraPositionState: CameraPositionState,
    userName: String,
    placeCount: Int,
    locationInfo: LocationModel,
    placeList: ImmutableList<AddedPlaceEntity>,
    placeCardList: ImmutableList<AddedMapPostEntity>,
    onExploreButtonClick: () -> Unit,
    onPlaceItemClick: (Int) -> Unit,
    onPlaceCardClick: (Int) -> Unit,
    navigateToMapSearch: () -> Unit,
    onBackButtonClick: () -> Unit,
    moveCamera: (Double, Double) -> Unit,
    onGpsButtonClick: () -> Unit
) {
    val systemPaddingValues = WindowInsets.systemBars.asPaddingValues()
    val density = LocalDensity.current

    val sheetState = rememberBottomSheetState(
        initialValue = AdvancedSheetState.PartiallyExpanded,
        defineValues = {
            AdvancedSheetState.Collapsed at height(dragHandleHeight.dp + paddingValues.calculateBottomPadding() + systemPaddingValues.calculateBottomPadding())
            AdvancedSheetState.PartiallyExpanded at height(60)
            AdvancedSheetState.Expanded at height(100)
        }
    )
    val scaffoldState = rememberBottomSheetScaffoldState(sheetState)

    val gpsIconOffset = with(density) { 85.dp.toPx() }

    var isMarkerSelected by remember { mutableStateOf(false) }
    var selectedMarkerId by remember { mutableIntStateOf(-1) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        NaverMap(
            modifier = Modifier
                .fillMaxHeight(
                    animateFloatAsState(
                        targetValue = calculateMapHeight(
                            isMarkerSelected = isMarkerSelected,
                            sheetState = sheetState
                        ),
                        label = ""
                    ).value
                ),
            cameraPositionState = cameraPositionState,
            locationSource = rememberCustomLocationSource(),
            uiSettings = MapUiSettings(
                isZoomControlEnabled = false,
                logoGravity = Gravity.TOP or Gravity.END,
                logoMargin = PaddingValues(end = 20.dp, top = 135.dp),
                isCompassEnabled = false
            ),
            properties = MapProperties(
                locationTrackingMode = LocationTrackingMode.Follow
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
                                moveCamera(place.latitude, place.longitude)
                            }
                            isMarkerSelected = selectedMarkerId == place.placeId
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
                        review = "",
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
            visible = sheetState.currentValue != AdvancedSheetState.Expanded && !isMarkerSelected,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOut(targetOffset = { IntOffset(0, it.height + gpsIconOffset.toInt()) }),
            modifier = Modifier
                .align(Alignment.TopEnd)
        ) {
            Box(
                modifier = Modifier
                    .padding(end = 20.dp)
                    .offset { IntOffset(0, (sheetState.offset + gpsIconOffset).toInt()) }
                    .noRippleClickable(onClick = onGpsButtonClick)
                    .size(44.dp)
                    .background(
                        color = SpoonyAndroidTheme.colors.white,
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_gps_24),
                    contentDescription = null,
                    tint = SpoonyAndroidTheme.colors.gray500,
                    modifier = Modifier
                        .padding(10.dp)
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (locationInfo.placeId == null) {
                MapTopAppBar(
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

            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier
                    .padding(vertical = 8.dp)
            ) {
                items(6) { index ->
                    IconChip(
                        text = "전체",
                        selectedIconUrl = "https://avatars.githubusercontent.com/u/200387868?s=48&v=4",
                        unSelectedIconUrl = "https://avatars.githubusercontent.com/u/200387868?s=48&v=4",
                        onClick = { },
                        isSelected = index == 0,
                        isGradient = true,
                        secondColor = SpoonyAndroidTheme.colors.white,
                        mainColor = SpoonyAndroidTheme.colors.main400,
                        selectedBorderColor = SpoonyAndroidTheme.colors.main200
                    )
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
                        Box {
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
                                                review = "",
                                                imageUrl = photoUrl,
                                                categoryIconUrl = categoryInfo.iconUrl,
                                                categoryName = categoryInfo.categoryName,
                                                textColor = Color.hexToColor(categoryInfo.textColor.toValidHexColor()),
                                                backgroundColor = Color.hexToColor(categoryInfo.backgroundColor.toValidHexColor()),
                                                onClick = {
                                                    onPlaceItemClick(placeId)
                                                    moveCamera(addedPlace.latitude, addedPlace.longitude)
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
                        }
                    },
                    sheetSwipeEnabled = placeList.isNotEmpty()
                ) {}
            }
        }
    }
}

@Composable
private fun CalculateDefaultHeight() {
    MapBottomSheetDragHandle(
        name = "",
        resultCount = 0,
        modifier = Modifier
            .onGloballyPositioned {
                dragHandleHeight = it.size.height
            }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun calculateMapHeight(
    isMarkerSelected: Boolean,
    sheetState: BottomSheetState<AdvancedSheetState>
): Float {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val density = LocalDensity.current.density

    return with(sheetState) {
        when {
            isMarkerSelected -> 1f

            currentValue == AdvancedSheetState.PartiallyExpanded && draggableState.targetValue == AdvancedSheetState.Collapsed -> {
                (1f - sheetState.sheetVisibleHeight / (screenHeight * density) + 0.1f).coerceAtLeast(MIN_PARTIALLY_HEIGHT)
            }

            currentValue == AdvancedSheetState.Collapsed && draggableState.targetValue == AdvancedSheetState.PartiallyExpanded -> {
                (1f - sheetState.sheetVisibleHeight / (screenHeight * density) + 0.1f).coerceAtLeast(MIN_PARTIALLY_HEIGHT)
            }

            currentValue == AdvancedSheetState.Collapsed -> COLLAPSED_HEIGHT

            else -> MIN_PARTIALLY_HEIGHT
        }
    }
}

@Composable
private fun rememberCustomLocationSource(
    isCompassEnabled: Boolean = false
): LocationSource {
    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    val context = LocalContext.current
    val locationSource = remember {
        object : FusedLocationSource(context) {

            override fun hasPermissions(): Boolean {
                return locationPermissions.all { permission ->
                    ContextCompat.checkSelfPermission(
                        context,
                        permission
                    ) == PackageManager.PERMISSION_GRANTED
                }
            }

            override fun onPermissionRequest() {}
        }
    }

    LaunchedEffect(isCompassEnabled) {
        locationSource.setCompassEnabled(enabled = isCompassEnabled)
    }
    return locationSource
}

@OptIn(ExperimentalNaverMapApi::class)
private fun moveCamera(
    cameraPositionState: CameraPositionState,
    latLng: LatLng
) {
    cameraPositionState.move(
        CameraUpdate
            .scrollAndZoomTo(
                latLng,
                DEFAULT_ZOOM
            )
            .animate(CameraAnimation.Easing)
    )
}

@SuppressLint("MissingPermission")
private fun getLastLocation(
    fusedLocationProviderClient: FusedLocationProviderClient,
    onLocationReceived: (Location) -> Unit
) {
    fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
        location?.let {
            onLocationReceived(it)
        }
    }
}

private object DefaultHeight {
    const val COLLAPSED_HEIGHT = 0.9f
    const val MIN_PARTIALLY_HEIGHT = 0.55f

    var dragHandleHeight = 86
}
