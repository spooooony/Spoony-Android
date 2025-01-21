package com.spoony.spoony.presentation.placeDetail

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.button.SpoonyButton
import com.spoony.spoony.core.designsystem.component.tag.IconTag
import com.spoony.spoony.core.designsystem.component.topappbar.TagTopAppBar
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.type.ButtonSize
import com.spoony.spoony.core.designsystem.type.ButtonStyle
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.core.util.extension.hexToColor
import com.spoony.spoony.core.util.extension.noRippleClickable
import com.spoony.spoony.core.util.extension.toValidHexColor
import com.spoony.spoony.domain.entity.CategoryEntity
import com.spoony.spoony.domain.entity.PostEntity
import com.spoony.spoony.domain.entity.UserEntity
import com.spoony.spoony.presentation.placeDetail.component.IconDropdownMenu
import com.spoony.spoony.presentation.placeDetail.component.PlaceDetailImageLazyRow
import com.spoony.spoony.presentation.placeDetail.component.ScoopDialog
import com.spoony.spoony.presentation.placeDetail.component.StoreInfo
import com.spoony.spoony.presentation.placeDetail.component.UserProfileInfo
import com.spoony.spoony.presentation.placeDetail.type.DropdownOption
import kotlinx.collections.immutable.ImmutableList

@Composable
fun PlaceDetailRoute(
    paddingValues: PaddingValues,
    navigateToReport: () -> Unit,
    navigateUp: () -> Unit,
    viewModel: PlaceDetailViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    val state by viewModel.state.collectAsStateWithLifecycle(lifecycleOwner = lifecycleOwner)

    val spoonAmount = when (state.spoonAmountEntity) {
        is UiState.Success -> (state.spoonAmountEntity as UiState.Success<Int>).data
        else -> 99
    }

    val userProfile = when (state.userEntity) {
        is UiState.Success -> (state.userEntity as UiState.Success<UserEntity>).data
        else -> UserEntity(
            userId = -1,
            userEmail = "test@email.com",
            userProfileUrl = "https://avatars.githubusercontent.com/u/93641814?v=4",
            userName = "안세홍",
            userRegion = "성북구"
        )
    }
    when (state.postEntity) {
        is UiState.Empty -> {}
        is UiState.Loading -> {}
        is UiState.Failure -> {}
        is UiState.Success -> {
            with(state.postEntity as UiState.Success<PostEntity>) {
                val postId = (state.postId as? UiState.Success)?.data ?: return
                val userId = (state.userId as? UiState.Success)?.data ?: return
                PlaceDetailScreen(
                    paddingValues = paddingValues,
                    menuList = data.menuList,
                    title = data.title,
                    description = data.description,
                    userProfileUrl = userProfile.userProfileUrl,
                    userName = userProfile.userName,
                    userRegion = userProfile.userRegion,
                    photoUrlList = data.photoUrlList,
                    category = data.category,
                    date = data.date,
                    placeAddress = data.placeAddress,
                    placeName = data.placeName,
                    spoonAmount = spoonAmount,
                    addMapCount = data.addMapCount,
                    isScooped = data.isScooped,
                    isAddMap = data.isAddMap,
                    latitude = data.latitude,
                    longitude = data.longitude,
                    onScoopButtonClick = { viewModel.useSpoon(postId, userId) },
                    onAddMapButtonClick = { viewModel.addMyMap(postId, userId) },
                    onDeletePinMapButtonClick = { viewModel.deletePinMap(postId, userId) },
                    dropdownMenuList = state.dropDownMenuList,
                    onBackButtonClick = navigateUp,
                    onReportButtonClick = navigateToReport
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
    date: String,
    placeAddress: String,
    placeName: String,
    spoonAmount: Int,
    addMapCount: Int,
    isAddMap: Boolean,
    isScooped: Boolean,
    latitude: Double,
    longitude: Double,
    onScoopButtonClick: () -> Unit,
    onAddMapButtonClick: () -> Unit,
    onDeletePinMapButtonClick: () -> Unit,
    onBackButtonClick: () -> Unit,
    dropdownMenuList: ImmutableList<String>,
    onReportButtonClick: () -> Unit
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    var scoopDialogVisibility by remember { mutableStateOf(false) }

    if (scoopDialogVisibility) {
        ScoopDialog(
            onClickPositive = {
                onScoopButtonClick()
                scoopDialogVisibility = false
            },
            onClickNegative = {
                scoopDialogVisibility = false
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = paddingValues.calculateBottomPadding())
    ) {
        TagTopAppBar(
            count = spoonAmount,
            showBackButton = true,
            onBackButtonClick = onBackButtonClick
        )
        Column(
            modifier = Modifier
                .weight(1f)
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
                    location = "$userRegion 수저",
                    modifier = Modifier.weight(1f)
                )

                IconDropdownMenu(
                    menuItems = dropdownMenuList,
                    onMenuItemClick = { menu ->
                        when (menu) {
                            DropdownOption.REPORT.string -> {
                                onReportButtonClick()
                            }
                        }
                    }
                )
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
                    text = date,
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
        PlaceDetailBottomBar(
            addMapCount = addMapCount,
            isScooped = isScooped,
            isAddMap = isAddMap,
            onScoopButtonClick = {
                scoopDialogVisibility = true
            },
            onSearchMapClick = {
                searchPlaceNaverMap(
                    latitude = latitude,
                    longitude = longitude,
                    placeName = placeName,
                    context = context
                )
            },
            onAddMapButtonClick = onAddMapButtonClick,
            onDeletePinMapButtonClick = onDeletePinMapButtonClick
        )
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

@Composable
private fun PlaceDetailBottomBar(
    addMapCount: Int,
    onScoopButtonClick: () -> Unit,
    onSearchMapClick: () -> Unit,
    onAddMapButtonClick: () -> Unit,
    onDeletePinMapButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    isScooped: Boolean = false,
    isAddMap: Boolean = false
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(SpoonyAndroidTheme.colors.white)
            .padding(
                horizontal = 20.dp,
                vertical = 10.dp
            )
            .height(IntrinsicSize.Max),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isScooped) {
            SpoonyButton(
                text = "길찾기",
                onClick = onSearchMapClick,
                style = ButtonStyle.Secondary,
                size = ButtonSize.Medium,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(15.dp))

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .sizeIn(minWidth = 56.dp)
                    .noRippleClickable(
                        if (isAddMap) {
                            onDeletePinMapButtonClick
                        } else {
                            onAddMapButtonClick
                        }
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = if (isAddMap) ImageVector.vectorResource(id = R.drawable.ic_add_map_main400_24) else ImageVector.vectorResource(id = R.drawable.ic_add_map_gray400_24),
                    modifier = Modifier
                        .size(32.dp),
                    contentDescription = null,
                    tint = Color.Unspecified
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = addMapCount.toString(),
                    style = SpoonyAndroidTheme.typography.caption1m,
                    color = SpoonyAndroidTheme.colors.gray800
                )
            }
        } else {
            SpoonyButton(
                text = "떠먹기",
                style = ButtonStyle.Secondary,
                size = ButtonSize.Medium,
                onClick = onScoopButtonClick,
                modifier = Modifier.weight(1f),
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_button_spoon_32),
                        modifier = Modifier.size(32.dp),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
            )
        }
    }
}
