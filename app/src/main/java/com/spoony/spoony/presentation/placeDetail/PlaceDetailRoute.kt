package com.spoony.spoony.presentation.placeDetail

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.spoony.spoony.core.designsystem.component.snackbar.TextSnackbar
import com.spoony.spoony.core.designsystem.component.tag.IconTag
import com.spoony.spoony.core.designsystem.component.topappbar.TagTopAppBar
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.core.util.extension.formatToYearMonthDay
import com.spoony.spoony.core.util.extension.hexToColor
import com.spoony.spoony.core.util.extension.toValidHexColor
import com.spoony.spoony.domain.entity.CategoryEntity
import com.spoony.spoony.domain.entity.UserEntity
import com.spoony.spoony.presentation.main.SHOW_SNACKBAR_TIMEMILLIS
import com.spoony.spoony.presentation.placeDetail.component.IconDropdownMenu
import com.spoony.spoony.presentation.placeDetail.component.PlaceDetailBottomBar
import com.spoony.spoony.presentation.placeDetail.component.PlaceDetailImageLazyRow
import com.spoony.spoony.presentation.placeDetail.component.ScoopDialog
import com.spoony.spoony.presentation.placeDetail.component.StoreInfo
import com.spoony.spoony.presentation.placeDetail.component.UserProfileInfo
import com.spoony.spoony.presentation.placeDetail.model.PostModel
import com.spoony.spoony.presentation.placeDetail.type.DropdownOption
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PlaceDetailRoute(
    paddingValues: PaddingValues,
    navigateToReport: (postId: Int, userId: Int) -> Unit,
    navigateUp: () -> Unit,
    viewModel: PlaceDetailViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    val state by viewModel.state.collectAsStateWithLifecycle(lifecycleOwner = lifecycleOwner)

    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    var scoopDialogVisibility by remember { mutableStateOf(false) }

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
            }
        }
    }

    val spoonAmount = when (state.spoonCount) {
        is UiState.Success -> (state.spoonCount as UiState.Success<Int>).data
        else -> 0
    }

    val postId = (state.postId as? UiState.Success)?.data ?: return

    val context = LocalContext.current

    val userProfile = when (state.userEntity) {
        is UiState.Success -> (state.userEntity as UiState.Success<UserEntity>).data
        else -> UserEntity(
            userId = -1,
            userProfileUrl = "",
            userName = "",
            userRegion = ""
        )
    }

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

    when (state.postModel) {
        is UiState.Empty -> {}
        is UiState.Loading -> {}
        is UiState.Failure -> {}
        is UiState.Success -> {
            with(state.postModel as UiState.Success<PostModel>) {
                val dropDownMenuList = when (data.isMine) {
                    true -> persistentListOf()
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
                            onBackButtonClick = navigateUp
                        )
                    },
                    bottomBar = {
                        PlaceDetailBottomBar(
                            modifier = Modifier
                                .navigationBarsPadding(),
                            addMapCount = state.addMapCount,
                            isScooped = state.isScooped || data.isMine,
                            isAddMap = state.isAddMap,
                            onScoopButtonClick = {
                                scoopDialogVisibility = true
                            },
                            onSearchMapClick = {
                                searchPlaceNaverMap(
                                    latitude = data.latitude,
                                    longitude = data.longitude,
                                    placeName = data.placeName,
                                    context = context
                                )
                            },
                            onAddMapButtonClick = { viewModel.addMyMap(postId) },
                            onDeletePinMapButtonClick = { viewModel.deletePinMap(postId) }
                        )
                    },
                    content = { paddingValues ->
                        PlaceDetailScreen(
                            paddingValues = paddingValues,
                            menuList = data.menuList.toImmutableList(),
                            title = data.title,
                            description = data.description,
                            userProfileUrl = userProfile.userProfileUrl,
                            userName = userProfile.userName,
                            userRegion = userProfile.userRegion,
                            photoUrlList = data.photoUrlList.toImmutableList(),
                            category = data.category,
                            date = data.date.formatToYearMonthDay(),
                            placeAddress = data.placeAddress,
                            placeName = data.placeName,
                            isScooped = state.isScooped || data.isMine,
                            dropdownMenuList = dropDownMenuList,
                            onReportButtonClick = { navigateToReport(postId, userProfile.userId) }
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
    title: String,
    description: String,
    userProfileUrl: String,
    userName: String,
    userRegion: String,
    photoUrlList: ImmutableList<String>,
    category: CategoryEntity,
    date: String?,
    placeAddress: String,
    placeName: String,
    isScooped: Boolean,
    dropdownMenuList: ImmutableList<DropdownOption>,
    onReportButtonClick: () -> Unit
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
                .padding(
                    vertical = 8.dp,
                    horizontal = 20.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            UserProfileInfo(
                imageUrl = userProfileUrl,
                name = userName,
                location = "서울시 $userRegion 수저",
                modifier = Modifier.weight(1f)
            )
            if (dropdownMenuList.isNotEmpty()) {
                IconDropdownMenu(
                    menuItems = dropdownMenuList,
                    onMenuItemClick = { menu ->
                        when (menu) {
                            DropdownOption.REPORT.name -> {
                                onReportButtonClick()
                            }
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        PlaceDetailImageLazyRow(
            imageList = photoUrlList,
            isBlurred = !isScooped
        )

        Spacer(modifier = Modifier.height(32.dp))

        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            IconTag(
                text = category.categoryName,
                backgroundColor = Color.hexToColor(category.backgroundColor.toValidHexColor()),
                textColor = Color.hexToColor(category.textColor.toValidHexColor()),
                iconUrl = category.iconUrl
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                style = SpoonyAndroidTheme.typography.title1,
                color = SpoonyAndroidTheme.colors.black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = date ?: "",
                style = SpoonyAndroidTheme.typography.caption1m,
                color = SpoonyAndroidTheme.colors.gray400
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = description,
                style = SpoonyAndroidTheme.typography.body2m,
                color = SpoonyAndroidTheme.colors.gray900
            )

            Spacer(modifier = Modifier.height(32.dp))

            StoreInfo(
                isBlurred = !isScooped,
                menuList = menuList,
                locationSubTitle = placeName,
                location = placeAddress
            )
        }
        Spacer(modifier = Modifier.height(27.dp))
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
