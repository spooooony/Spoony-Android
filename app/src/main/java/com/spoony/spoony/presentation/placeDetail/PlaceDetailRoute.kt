package com.spoony.spoony.presentation.placeDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.spoony.spoony.core.util.extension.noRippleClickable
import com.spoony.spoony.domain.entity.IconTagEntity
import com.spoony.spoony.domain.entity.PostEntity
import com.spoony.spoony.domain.entity.UserEntity
import com.spoony.spoony.presentation.placeDetail.component.IconDropdownMenu
import com.spoony.spoony.presentation.placeDetail.component.PlaceDetailImageLazyRow
import com.spoony.spoony.presentation.placeDetail.component.StoreInfo
import com.spoony.spoony.presentation.placeDetail.component.UserProfileInfo
import kotlinx.collections.immutable.ImmutableList

@Composable
fun PlaceDetailRoute(
    viewModel: PlaceDetailViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    val state by viewModel.state.collectAsStateWithLifecycle(lifecycleOwner = lifecycleOwner)

    val spoonAmount = when (state.spoonAmountEntity) {
        is UiState.Success -> (state.spoonAmountEntity as UiState.Success<Int>).data
        else -> 0
    }

    val userProfile = when (state.userEntity) {
        is UiState.Success -> (state.userEntity as UiState.Success<UserEntity>).data
        else -> UserEntity(
            userProfileUrl = "",
            userName = "",
            userRegion = ""
        )
    }

    with(state.postEntity as UiState.Success<PostEntity>) {
        when (state.postEntity) {
            is UiState.Empty -> {}
            is UiState.Loading -> {}
            is UiState.Failure -> {}
            is UiState.Success -> {
                PlaceDetailScreen(
                    menuList = data.menuList,
                    title = data.title,
                    description = data.description,
                    userProfileUrl = userProfile.userProfileUrl,
                    userName = userProfile.userName,
                    userRegion = userProfile.userRegion,
                    photoUrlList = data.photoUrlList,
                    categoryColorResponse = IconTagEntity(
                        name = data.categoryColorResponse.name,
                        backgroundColorHex = data.categoryColorResponse.backgroundColorHex,
                        textColorHex = data.categoryColorResponse.textColorHex,
                        iconUrl = data.categoryColorResponse.iconUrl
                    ),
                    date = data.date,
                    placeAddress = data.placeAddress,
                    placeName = data.placeName,
                    spoonAmount = spoonAmount,
                    zzinCount = data.zzinCount,
                    isScoop = data.isScoop,
                    isZzim = data.isZzim,
                    updateScoop = viewModel::updateScoop,
                    updateZzim = viewModel::updateZzim,
                    dropdownMenuList = state.dropDownMenuList,
                    onBackButtonClick = {}
                )
            }
        }
    }
}

@Composable
private fun PlaceDetailScreen(
    menuList: ImmutableList<String>,
    title: String,
    description: String,
    userProfileUrl: String,
    userName: String,
    userRegion: String,
    photoUrlList: ImmutableList<String>,
    categoryColorResponse: IconTagEntity,
    date: String,
    placeAddress: String,
    placeName: String,
    spoonAmount: Int,
    zzinCount: Int,
    isScoop: Boolean = false,
    isZzim: Boolean,
    updateScoop: (Boolean) -> Unit,
    updateZzim: (Boolean) -> Unit,
    onBackButtonClick: () -> Unit,
    dropdownMenuList: ImmutableList<String>
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .statusBarsPadding()
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
                    location = userRegion,
                    modifier = Modifier.weight(1f)
                )

                IconDropdownMenu(
                    menuItems = dropdownMenuList,
                    onMenuItemClick = { selectedItem ->
                        // 신고하기 클릭시 이벤트
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            PlaceDetailImageLazyRow(
                imageList = photoUrlList,
                isBlurred = !isScoop
            )

            Spacer(modifier = Modifier.height(32.dp))

            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                IconTag(
                    text = categoryColorResponse.name,
                    backgroundColorHex = categoryColorResponse.backgroundColorHex,
                    textColorHex = categoryColorResponse.textColorHex,
                    iconUrl = categoryColorResponse.iconUrl
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
                    isBlurred = !isScoop,
                    menuList = menuList,
                    locationSubTitle = placeName,
                    location = placeAddress
                )
            }
            Spacer(modifier = Modifier.height(27.dp))
        }
        PlaceDetailBottomBar(
            modifier = Modifier,
            count = zzinCount,
            isScoop = isScoop,
            isZzim = isZzim,
            onSpoonEatClick = updateScoop,
            onSearchMapClick = {
                // 네이버 길찾기 코드
            },
            onMyMapSelectClick = updateZzim
        )
    }
}

@Composable
fun PlaceDetailBottomBar(
    count: Int,
    onSpoonEatClick: (Boolean) -> Unit,
    onSearchMapClick: () -> Unit,
    onMyMapSelectClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    isScoop: Boolean = false,
    isZzim: Boolean = false
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(SpoonyAndroidTheme.colors.white)
            .padding(
                horizontal = 20.dp,
                vertical = 10.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isScoop) {
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
                    .width(56.dp)
                    .noRippleClickable { onMyMapSelectClick(isZzim) },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = if (!isZzim) ImageVector.vectorResource(id = R.drawable.ic_add_map_gray400_24) else ImageVector.vectorResource(id = R.drawable.ic_add_map_main400_24),
                    modifier = Modifier
                        .size(32.dp),
                    contentDescription = null,
                    tint = Color.Unspecified
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = count.toString(),
                    style = SpoonyAndroidTheme.typography.caption1m,
                    color = SpoonyAndroidTheme.colors.gray800
                )
            }
        } else {
            SpoonyButton(
                text = "떠먹기",
                style = ButtonStyle.Secondary,
                size = ButtonSize.Medium,
                onClick = { onSpoonEatClick(isScoop) },
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
