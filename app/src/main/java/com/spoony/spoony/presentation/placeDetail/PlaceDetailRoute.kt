package com.spoony.spoony.presentation.placeDetail

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.spoony.spoony.core.designsystem.component.button.FollowButton
import com.spoony.spoony.core.designsystem.component.snackbar.TextSnackbar
import com.spoony.spoony.core.designsystem.component.topappbar.TagTopAppBar
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.core.util.extension.formatToYearMonthDay
import com.spoony.spoony.presentation.main.SHOW_SNACKBAR_TIMEMILLIS
import com.spoony.spoony.presentation.placeDetail.component.DeleteReviewDialog
import com.spoony.spoony.presentation.placeDetail.component.DisappointItem
import com.spoony.spoony.presentation.placeDetail.component.IconDropdownMenu
import com.spoony.spoony.presentation.placeDetail.component.PlaceDetailBottomBar
import com.spoony.spoony.presentation.placeDetail.component.PlaceDetailImageLazyRow
import com.spoony.spoony.presentation.placeDetail.component.PlaceDetailSliderSection
import com.spoony.spoony.presentation.placeDetail.component.ScoopDialog
import com.spoony.spoony.presentation.placeDetail.component.StoreInfo
import com.spoony.spoony.presentation.placeDetail.component.UserProfileInfo
import com.spoony.spoony.presentation.placeDetail.model.PlaceDetailModel
import com.spoony.spoony.presentation.placeDetail.model.UserInfoModel
import com.spoony.spoony.presentation.placeDetail.type.DropdownOption
import com.spoony.spoony.presentation.register.model.RegisterType
import com.spoony.spoony.presentation.report.ReportType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PlaceDetailRoute(
    paddingValues: PaddingValues,
    navigateToReport: (reportTargetId: Int, type: ReportType) -> Unit,
    navigateToEditReview: (Int, RegisterType) -> Unit,
    navigateToUserProfile: (Int) -> Unit,
    navigateToAttendance: () -> Unit,
    navigateToMyPage: () -> Unit,
    navigateUp: () -> Unit,
    viewModel: PlaceDetailViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    val state by viewModel.state.collectAsStateWithLifecycle(lifecycleOwner = lifecycleOwner)

    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    var scoopDialogVisibility by remember { mutableStateOf(false) }
    var deleteReviewDialogVisibility by remember { mutableStateOf(false) }

    val onShowSnackBar: (String) -> Unit = { message ->
        coroutineScope.launch {
            snackBarHostState.currentSnackbarData?.dismiss()
            val job = launch {
                snackBarHostState.showSnackbar(message)
            }
            delay(SHOW_SNACKBAR_TIMEMILLIS)
            job.cancel()
        }
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle).collect { effect ->
            when (effect) {
                is PlaceDetailSideEffect.ShowSnackbar -> {
                    onShowSnackBar(effect.message)
                }
                is PlaceDetailSideEffect.NavigateUp -> navigateUp()
            }
        }
    }

    val lifecycle = lifecycleOwner.lifecycle
    LaunchedEffect(lifecycle) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.refresh()
        }
    }

    val spoonAmount = when (state.spoonCount) {
        is UiState.Success -> (state.spoonCount as UiState.Success<Int>).data
        else -> 0
    }

    val context = LocalContext.current

    val userProfile = when (state.userInfo) {
        is UiState.Success -> (state.userInfo as UiState.Success<UserInfoModel>).data
        else -> UserInfoModel(
            userId = -1,
            userProfileUrl = "",
            userName = "",
            userRegion = ""
        )
    }

    when (state.placeDetailModel) {
        is UiState.Empty -> {}
        is UiState.Loading -> {}
        is UiState.Failure -> {}
        is UiState.Success -> {
            val postId = (state.reviewId as? UiState.Success)?.data ?: return
            if (scoopDialogVisibility) {
                ScoopDialog(
                    onClickPositive = {
                        viewModel.useSpoon(postId)
                        scoopDialogVisibility = false
                    },
                    onClickNegative = {
                        scoopDialogVisibility = false
                    }
                )
            }
            if (deleteReviewDialogVisibility) {
                DeleteReviewDialog(
                    onClickPositive = {
                        viewModel.deleteReview(postId)
                        deleteReviewDialogVisibility = false
                    },
                    onClickNegative = {
                        deleteReviewDialogVisibility = false
                    }
                )
            }
            with(state.placeDetailModel as UiState.Success<PlaceDetailModel>) {
                val dropDownMenuList = when (data.isMine) {
                    true -> persistentListOf(
                        DropdownOption.EDIT,
                        DropdownOption.DELETE
                    )
                    false -> persistentListOf(DropdownOption.REPORT)
                }
                Scaffold(
                    snackbarHost = {
                        SnackbarHost(hostState = snackBarHostState) { snackbarData ->
                            TextSnackbar(text = snackbarData.visuals.message)
                        }
                    },
                    topBar = {
                        TagTopAppBar(
                            count = spoonAmount,
                            showBackButton = true,
                            onBackButtonClick = navigateUp,
                            onLogoTagClick = navigateToAttendance
                        )
                    },
                    bottomBar = {
                        PlaceDetailBottomBar(
                            modifier = Modifier
                                .navigationBarsPadding(),
                            addMapCount = state.addMapCount,
                            isAddMap = state.isAddMap,
                            onSearchMapClick = {
                                searchPlaceNaverMap(
                                    latitude = data.latitude,
                                    longitude = data.longitude,
                                    placeName = data.placeName,
                                    context = context
                                )
                            },
                            isNotMine = !data.isMine,
                            onAddMapButtonClick = { viewModel.addMyMap(postId) },
                            onDeletePinMapButtonClick = { viewModel.deletePinMap(postId) }
                        )
                    },
                    content = { paddingValues ->
                        PlaceDetailScreen(
                            paddingValues = paddingValues,
                            menuList = data.menuList,
                            description = data.description,
                            value = data.value,
                            cons = data.cons,
                            onScoopButtonClick = {
                                scoopDialogVisibility = true
                            },
                            onDeleteReviewClick = {
                                deleteReviewDialogVisibility = true
                            },
                            onUserProfileClick = {
                                if (data.isMine) {
                                    navigateToMyPage()
                                } else {
                                    navigateToUserProfile(userProfile.userId)
                                }
                            },
                            onEditReviewClick = { navigateToEditReview(postId, RegisterType.EDIT) },
                            userProfileUrl = userProfile.userProfileUrl,
                            userName = userProfile.userName,
                            userRegion = userProfile.userRegion,
                            isFollowing = state.isFollowing,
                            onFollowButtonClick = { viewModel.onFollowButtonClick(userProfile.userId, state.isFollowing) },
                            photoUrlList = data.photoUrlList,
                            date = data.createdAt.formatToYearMonthDay(),
                            placeAddress = data.placeAddress,
                            placeName = data.placeName,
                            isMine = data.isMine,
                            spoonAmount = spoonAmount,
                            isScooped = state.isScooped || data.isMine,
                            dropdownMenuList = dropDownMenuList,
                            onReportButtonClick = { navigateToReport(postId, ReportType.POST) },
                            onShowSnackBar = viewModel::showSnackBar
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun PlaceDetailScreen(
    paddingValues: PaddingValues,
    menuList: ImmutableList<String>,
    description: String,
    value: Double,
    cons: String,
    onScoopButtonClick: () -> Unit,
    onDeleteReviewClick: () -> Unit,
    onUserProfileClick: () -> Unit,
    onEditReviewClick: () -> Unit,
    userProfileUrl: String,
    userName: String,
    userRegion: String,
    isFollowing: Boolean,
    onFollowButtonClick: () -> Unit,
    photoUrlList: ImmutableList<String>,
    date: String,
    placeAddress: String,
    placeName: String,
    isMine: Boolean,
    spoonAmount: Int,
    isScooped: Boolean,
    dropdownMenuList: ImmutableList<DropdownOption>,
    onReportButtonClick: () -> Unit,
    onShowSnackBar: (String) -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(scrollState)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            UserProfileInfo(
                imageUrl = userProfileUrl,
                name = userName,
                region = userRegion,
                onClick = onUserProfileClick,
                modifier = Modifier.weight(1f)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (!isMine) {
                    FollowButton(
                        isFollowing = isFollowing,
                        onClick = onFollowButtonClick
                    )
                }
                if (dropdownMenuList.isNotEmpty()) {
                    IconDropdownMenu(
                        menuItems = dropdownMenuList,
                        onMenuItemClick = { menu ->
                            when (menu) {
                                DropdownOption.REPORT.name -> {
                                    onReportButtonClick()
                                }
                                DropdownOption.EDIT.name -> {
                                    onEditReviewClick()
                                }
                                DropdownOption.DELETE.name -> {
                                    onDeleteReviewClick()
                                }
                            }
                        },
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        PlaceDetailImageLazyRow(
            imageList = photoUrlList
        )

        Spacer(modifier = Modifier.height(15.dp))

        Column(
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            Text(
                text = description,
                style = SpoonyAndroidTheme.typography.body2m,
                color = SpoonyAndroidTheme.colors.gray900
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = date,
                style = SpoonyAndroidTheme.typography.caption1m,
                color = SpoonyAndroidTheme.colors.gray400
            )
            Spacer(modifier = Modifier.height(18.dp))
            StoreInfo(
                menuList = menuList,
                locationSubTitle = placeName,
                location = placeAddress
            )
            Spacer(modifier = Modifier.height(18.dp))
            PlaceDetailSliderSection(
                sliderPosition = value.toFloat()
            )
            if (cons.isNotEmpty()) {
                Spacer(modifier = Modifier.height(18.dp))
                DisappointItem(
                    cons = cons,
                    onScoopButtonClick = {
                        if (spoonAmount > 0) {
                            onScoopButtonClick()
                        } else {
                            onShowSnackBar("남은 스푼이 없어요 ㅠ.ㅠ")
                        }
                    },
                    isBlurred = !isScooped
                )
            }
        }
        Spacer(modifier = Modifier.height(45.dp))
    }
}

private fun searchPlaceNaverMap(
    latitude: Double,
    longitude: Double,
    placeName: String,
    context: Context
) {
    val url = "nmap://place?lat=$latitude&lng=$longitude&name=$placeName&appname=${context.packageName}"
    val isInstalled = try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.packageManager.getPackageInfo(
                "com.nhn.android.nmap",
                PackageManager.PackageInfoFlags.of(0)
            )
        } else {
            context.packageManager.getPackageInfo("com.nhn.android.nmap", 0)
        }
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
    if (isInstalled) {
        Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            addCategory(Intent.CATEGORY_BROWSABLE)
            context.startActivity(this)
        }
    } else {
        Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.nhn.android.nmap")).apply {
            context.startActivity(this)
        }
    }
}
