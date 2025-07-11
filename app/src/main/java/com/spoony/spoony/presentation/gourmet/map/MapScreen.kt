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
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.spoony.spoony.presentation.gourmet.map.DefaultHeight.COLLAPSED_HEIGHT
import com.spoony.spoony.presentation.gourmet.map.DefaultHeight.MIN_PARTIALLY_HEIGHT
import com.spoony.spoony.presentation.gourmet.map.DefaultHeight.dragHandleHeight
import com.spoony.spoony.presentation.gourmet.map.component.LocationPermissionDialog
import com.spoony.spoony.presentation.gourmet.map.component.MapCardPager
import com.spoony.spoony.presentation.gourmet.map.component.MapTopAppBar
import com.spoony.spoony.presentation.gourmet.map.component.SpoonyMapMarker
import com.spoony.spoony.presentation.gourmet.map.component.bottomsheet.MapBottomSheetDragHandle
import com.spoony.spoony.presentation.gourmet.map.component.bottomsheet.MapEmptyBottomSheetContent
import com.spoony.spoony.presentation.gourmet.map.component.bottomsheet.MapListItem
import com.spoony.spoony.presentation.gourmet.map.model.CategoryModel
import com.spoony.spoony.presentation.gourmet.map.model.LocationModel
import com.spoony.spoony.presentation.gourmet.map.model.PlaceReviewModel
import com.spoony.spoony.presentation.gourmet.map.model.ReviewCardModel
import io.morfly.compose.bottomsheet.material3.BottomSheetState
import io.morfly.compose.bottomsheet.material3.rememberBottomSheetScaffoldState
import io.morfly.compose.bottomsheet.material3.rememberBottomSheetState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

private const val DEFAULT_CATEGORY_ID = 1
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

    var shouldShowSystemDialog by remember { mutableStateOf(true) }

    val fusedLocationProviderClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    var isPermissionDialogVisible by remember { mutableStateOf(false) }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) {
        shouldShowSystemDialog = LOCATION_PERMISSIONS.any { permission ->
            ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, permission)
        }

        getLastLocation(fusedLocationProviderClient) { location ->
            moveCamera(
                cameraPositionState = cameraPositionState,
                latLng = LatLng(location.latitude, location.longitude)
            )
        }
    }

    BackHandler(enabled = state.locationModel.placeId != null) {
        viewModel.updateLocationModel(LocationModel())
    }

    SideEffect {
        systemUiController.setNavigationBarColor(
            color = white
        )
    }

    with(state.locationModel) {
        LaunchedEffect(placeId) {
            if (placeId == null) {
                viewModel.getAddedPlaceList(DEFAULT_CATEGORY_ID)
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
        when {
            state.locationModel.placeId != null -> {
                moveCamera(
                    cameraPositionState = cameraPositionState,
                    latLng = LatLng(state.locationModel.latitude, state.locationModel.longitude),
                    scale = state.locationModel.scale
                )
            }

            LOCATION_PERMISSIONS.any { permission ->
                ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
            } -> {
                getLastLocation(fusedLocationProviderClient) { location ->
                    moveCamera(
                        cameraPositionState = cameraPositionState,
                        latLng = LatLng(location.latitude, location.longitude)
                    )
                }
            }

            else -> locationPermissionLauncher.launch(LOCATION_PERMISSIONS)
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
        placeList = (state.addedPlaceList as? UiState.Success<ImmutableList<PlaceReviewModel>>)?.data ?: persistentListOf(),
        placeCardList = (state.placeCardInfo as? UiState.Success<ImmutableList<ReviewCardModel>>)?.data ?: persistentListOf(),
        categoryList = (state.categoryList as? UiState.Success<ImmutableList<CategoryModel>>)?.data ?: persistentListOf(),
        locationInfo = state.locationModel,
        onExploreButtonClick = navigateToExplore,
        onPlaceItemClick = viewModel::getPlaceInfo,
        onPlaceCardClick = navigateToPlaceDetail,
        navigateToMapSearch = navigateToMapSearch,
        onCloseButtonClick = { viewModel.updateLocationModel(LocationModel()) },
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
                locationPermissionLauncher.launch(LOCATION_PERMISSIONS)

                if (!shouldShowSystemDialog) {
                    isPermissionDialogVisible = true
                }
            }
        },
        onCategoryClick = viewModel::getAddedPlaceList
    )

    if (showSpoonDraw) {
        viewModel.updateLastEntryDate()

        SpoonDrawDialog(
            onDismiss = viewModel::checkSpoonDrawn,
            onSpoonDrawButtonClick = viewModel::drawSpoon,
            onConfirmButtonClick = {
                viewModel.checkSpoonDrawn()
                navigateToAttendance()
            }
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
    placeList: ImmutableList<PlaceReviewModel>,
    placeCardList: ImmutableList<ReviewCardModel>,
    categoryList: ImmutableList<CategoryModel>,
    onExploreButtonClick: () -> Unit,
    onPlaceItemClick: (Int) -> Unit,
    onPlaceCardClick: (Int) -> Unit,
    navigateToMapSearch: () -> Unit,
    onCloseButtonClick: () -> Unit,
    moveCamera: (Double, Double) -> Unit,
    onGpsButtonClick: () -> Unit,
    onCategoryClick: (Int) -> Unit
) {
    val density = LocalDensity.current

    val sheetState = rememberBottomSheetState(
        initialValue = AdvancedSheetState.PartiallyExpanded,
        defineValues = {
            AdvancedSheetState.Collapsed at height(dragHandleHeight.dp + paddingValues.calculateBottomPadding())
            AdvancedSheetState.PartiallyExpanded at height(60)
            AdvancedSheetState.Expanded at height(100)
        }
    )
    val scaffoldState = rememberBottomSheetScaffoldState(sheetState)

    var topAppBarHeight by remember { mutableStateOf(0.dp) }
    var chipHeight by remember { mutableStateOf(0.dp) }
    val gpsIconOffset = with(density) { 45.dp.plus(chipHeight).toPx() }

    var isMarkerSelected by remember { mutableStateOf(false) }
    var selectedMarkerId by remember { mutableIntStateOf(-1) }
    var selectedCategoryId by remember { mutableIntStateOf(1) }

    LaunchedEffect(placeList.isEmpty()) {
        if (placeList.isEmpty()) scaffoldState.sheetState.animateTo(AdvancedSheetState.PartiallyExpanded)
    }

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
                logoMargin = PaddingValues(end = 20.dp, top = topAppBarHeight.plus(chipHeight).plus(42.dp)),
                isCompassEnabled = false
            ),
            properties = MapProperties(
                locationTrackingMode = LocationTrackingMode.NoFollow
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
                        captionText = if (cameraPositionState.position.zoom > 10.0) place.placeName else "",
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
                .padding(vertical = 5.dp),
            visible = isMarkerSelected,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOut(targetOffset = { IntOffset(0, it.height) })
        ) {
            MapCardPager(
                placeCardList = placeCardList,
                onPlaceCardClick = onPlaceCardClick
            )
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
                        .onGloballyPositioned {
                            topAppBarHeight = with(density) { it.size.height.toDp() }
                        }
                )
            } else {
                CloseTopAppBar(
                    title = locationInfo.placeName ?: "",
                    onCloseButtonClick = onCloseButtonClick,
                    modifier = Modifier
                        .onGloballyPositioned {
                            topAppBarHeight = with(density) { it.size.height.toDp().plus(16.dp) }
                        }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (locationInfo.placeId == null) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                ) {
                    items(categoryList) { category ->
                        with(category) {
                            IconChip(
                                text = category.categoryName,
                                selectedIconUrl = iconUrl,
                                unSelectedIconUrl = unSelectedIconUrl,
                                onClick = {
                                    selectedCategoryId = categoryId
                                    onCategoryClick(categoryId)
                                },
                                isSelected = categoryId == selectedCategoryId,
                                isGradient = true,
                                secondColor = SpoonyAndroidTheme.colors.white,
                                mainColor = SpoonyAndroidTheme.colors.main400,
                                selectedBorderColor = SpoonyAndroidTheme.colors.main200,
                                modifier = Modifier.onGloballyPositioned {
                                    chipHeight = with(density) { it.size.height.toDp().plus(8.dp) }
                                }
                            )
                        }
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
                                contentPadding = PaddingValues(top = 6.dp),
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
                                            review = description,
                                            imageUrl = photoUrl,
                                            categoryIconUrl = categoryInfo.iconUrl,
                                            categoryName = categoryInfo.categoryName,
                                            textColor = Color.hexToColor(categoryInfo.textColor),
                                            backgroundColor = Color.hexToColor(categoryInfo.backgroundColor),
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
    latLng: LatLng,
    scale: Double = DEFAULT_ZOOM
) {
    cameraPositionState.move(
        CameraUpdate
            .scrollAndZoomTo(
                latLng,
                scale
            )
            .animate(CameraAnimation.Fly)
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
    const val MIN_PARTIALLY_HEIGHT = 0.6f

    var dragHandleHeight = 86
}
