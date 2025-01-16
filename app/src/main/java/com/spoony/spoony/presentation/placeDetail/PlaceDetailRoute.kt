package com.spoony.spoony.presentation.placeDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.button.SpoonyButton
import com.spoony.spoony.core.designsystem.component.tag.IconTag
import com.spoony.spoony.core.designsystem.component.topappbar.TagTopAppBar
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.type.ButtonSize
import com.spoony.spoony.core.designsystem.type.ButtonStyle
import com.spoony.spoony.core.util.extension.noRippleClickable
import com.spoony.spoony.presentation.map.navigaion.navigateToMap
import com.spoony.spoony.presentation.placeDetail.component.IconDropdownMenu
import com.spoony.spoony.presentation.placeDetail.component.PlaceDetailImageLazyRow
import com.spoony.spoony.presentation.placeDetail.component.StoreInfo
import com.spoony.spoony.presentation.placeDetail.component.UserProfileInfo
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.immutableListOf

data class IconTag(
    val name: String,
    val backgroundColorHex: String,
    val textColorHex: String,
    val iconUrl: String
)

@Composable
fun PlaceDetailRoute(
    navController: NavController
) {
    PlaceDetailScreen(
        navigateToMap = { navController.navigateToMap() }
    )
}

@Composable
private fun PlaceDetailScreen(
    navigateToMap: () -> Unit,
    menuItems: ImmutableList<String> = immutableListOf(
        "고등어봉초밥",
        "크렘브륄레",
        "사케"
    ),
    textTile: String = "인생 이자카야. 고등어 초밥 안주가 그냥 미쳤어요.",
    textContent: String = "이자카야인데 친구랑 가서 안주만 5개 넘게 시킴.. 명성이 자자한 고등어봉 초밥은 꼭 시키세요! 입에 넣자마자 사르르 녹아 없어짐. 그리고 밤 후식 진짜 맛도리니까 밤 디저트 좋아하는 사람이면 꼭 먹어보기! ",
    profileUrl: String = "https://gratisography.com/wp-content/uploads/2024/10/gratisography-cool-cat-800x525.jpg",
    profileName: String = "안세홍",
    profileLocation: String = "성북구 수저",
    dropdownMenuList: ImmutableList<String> = immutableListOf("신고하기"),
    imageList: ImmutableList<String> = immutableListOf(
        "https://scontent-ssn1-1.cdninstagram.com/v/t51.29350-15/473779988_950264370546560_5341501240589886868_n.jpg?stp=dst-jpg_e35_tt7&efg=eyJ2ZW5jb2RlX3RhZyI6ImltYWdlX3VybGdlbi4xNDQweDE4MDAuc2RyLmYyOTM1MC5kZWZhdWx0X2ltYWdlIn0&_nc_ht=scontent-ssn1-1.cdninstagram.com&_nc_cat=108&_nc_ohc=pHHeQnTQ2MMQ7kNvgFAMpBC&_nc_gid=ade93f1afc274f04a82c92d9b55d5753&edm=APs17CUBAAAA&ccb=7-5&ig_cache_key=MzU0NjA2ODI2MjUyMDI2ODQxNA%3D%3D.3-ccb7-5&oh=00_AYCoKFozJIoZG9Izmc5UtfR5Gg__iKqdIG_lBiKdHBHHoQ&oe=678DA05C&_nc_sid=10d13b",
        "https://scontent-ssn1-1.cdninstagram.com/v/t51.29350-15/473777300_2608644809333752_6829001915967720892_n.jpg?stp=dst-jpegr_e35_tt6&efg=eyJ2ZW5jb2RlX3RhZyI6ImltYWdlX3VybGdlbi4xNDQweDE4MDAuaGRyLmYyOTM1MC5kZWZhdWx0X2ltYWdlIn0&_nc_ht=scontent-ssn1-1.cdninstagram.com&_nc_cat=104&_nc_ohc=jGWaaI-t3NgQ7kNvgG_G7jW&_nc_gid=ade93f1afc274f04a82c92d9b55d5753&edm=APs17CUBAAAA&ccb=7-5&ig_cache_key=MzU0NjA2ODI2MjkzMTE3ODc4Ng%3D%3D.3-ccb7-5&oh=00_AYA2Yk3eVifWtHRTdKnlzzrSExKSY53mP1SoTgVhRS9WZA&oe=678DA27E&_nc_sid=10d13b",
        "https://scontent-ssn1-1.cdninstagram.com/v/t51.29350-15/473699784_1020999916728583_5213195047254766315_n.jpg?stp=dst-jpegr_e35_tt6&efg=eyJ2ZW5jb2RlX3RhZyI6ImltYWdlX3VybGdlbi4xNDQweDE0NDAuaGRyLmYyOTM1MC5kZWZhdWx0X2ltYWdlIn0&_nc_ht=scontent-ssn1-1.cdninstagram.com&_nc_cat=107&_nc_ohc=Jsa46D-cPKQQ7kNvgEb3Ibg&_nc_gid=43b0cc3eec544f7b82ac6ce4d0931342&edm=AP4sbd4BAAAA&ccb=7-5&ig_cache_key=MzU0NDg1ODAzMzAwMjU2MzI2Nw%3D%3D.3-ccb7-5&oh=00_AYAUoYMY3WAk816Pv1ntEDKsuGiRyiIqIIEtN2n_rNDOzA&oe=678DAEA4&_nc_sid=7a9f4b",
        "https://scontent-ssn1-1.cdninstagram.com/v/t51.29350-15/473668872_631155566092547_2449423066645547426_n.jpg?stp=dst-jpegr_e35_tt6&efg=eyJ2ZW5jb2RlX3RhZyI6ImltYWdlX3VybGdlbi4xNDQweDE4MDAuaGRyLmYyOTM1MC5kZWZhdWx0X2ltYWdlIn0&_nc_ht=scontent-ssn1-1.cdninstagram.com&_nc_cat=111&_nc_ohc=R493Nwe8XM4Q7kNvgGceBQv&_nc_gid=43b0cc3eec544f7b82ac6ce4d0931342&edm=AP4sbd4BAAAA&ccb=7-5&ig_cache_key=MzU0NDY0OTE1NzAwNzA2MzU2Mg%3D%3D.3-ccb7-5&oh=00_AYDU1LNSGLa4GKAZQRlX5ABzJQ4qf5h62z257zioGFlesA&oe=678DABD6&_nc_sid=7a9f4b",
        "https://scontent-ssn1-1.cdninstagram.com/v/t51.29350-15/473560284_568265156121387_7921460915182213394_n.jpg?stp=dst-jpegr_e35_tt6&efg=eyJ2ZW5jb2RlX3RhZyI6ImltYWdlX3VybGdlbi4xNDQweDE4MDAuaGRyLmYyOTM1MC5kZWZhdWx0X2ltYWdlIn0&_nc_ht=scontent-ssn1-1.cdninstagram.com&_nc_cat=103&_nc_ohc=gKoX2mv1x0QQ7kNvgHd6AjB&_nc_gid=af16ea24a7d04cc9a0d1e86a87ffccac&edm=APoiHPcBAAAA&ccb=7-5&ig_cache_key=MzU0NDU3MjM1MzUwNDM2MDA5Ng%3D%3D.3-ccb7-5&oh=00_AYDc2sbybqTbqQJaVw9HkW-Zt4aaVElCM-ecAUheJdCyGQ&oe=678DAE1A&_nc_sid=22de04"
    ),
    tag: IconTag = IconTag(
        name = "주류",
        backgroundColorHex = "EEE3FD",
        textColorHex = "AD75F9",
        iconUrl = "https://github.com/user-attachments/assets/b492c9c7-0796-4312-9b32-7ae474700bd0"
    ),
    dateString: String = "2025년 1월 2일",
    locationAddress: String = "서울 마포구 연희로11가길 39",
    locationName: String = "어키",
    locationPinCount: Int = 99,
    mySpoonCount: Int = 99
) {
    var isSpoonEat by remember { mutableStateOf(false) }
    var isMyMap by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .statusBarsPadding()
    ) {
        TagTopAppBar(
            count = mySpoonCount,
            showBackButton = true,
            onBackButtonClick = navigateToMap
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.dp)
                .verticalScroll(rememberScrollState())
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
                    imageUrl = profileUrl,
                    name = profileName,
                    location = profileLocation,
                    modifier = Modifier.weight(1f)
                )
                IconDropdownMenu(
                    menuItems = dropdownMenuList,
                    onMenuItemClick = { selectedItem ->
                        // selectedItem
                    }
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            PlaceDetailImageLazyRow(
                imageList = imageList,
                isBlurred = !isSpoonEat
            )
            Spacer(modifier = Modifier.height(32.dp))
            IconTag(
                text = tag.name,
                backgroundColorHex = tag.backgroundColorHex,
                textColorHex = tag.textColorHex,
                iconUrl = tag.iconUrl,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = textTile,
                style = SpoonyAndroidTheme.typography.title1,
                color = SpoonyAndroidTheme.colors.black,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = dateString,
                style = SpoonyAndroidTheme.typography.caption1m,
                color = SpoonyAndroidTheme.colors.gray400,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = textContent,
                style = SpoonyAndroidTheme.typography.body2m,
                color = SpoonyAndroidTheme.colors.gray900,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            StoreInfo(
                modifier = Modifier.padding(horizontal = 20.dp),
                isBlurred = !isSpoonEat,
                menuItems = menuItems,
                locationSubTitle = locationName,
                location = locationAddress
            )
            Spacer(modifier = Modifier.height(103.dp))
        }
        PlaceDetailBottomBar(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            count = locationPinCount,
            isSpoonEat = isSpoonEat,
            isMyMap = isMyMap,
            onSpoonEatClick = {
                isSpoonEat = !isSpoonEat
            },
            onSearchMapClick = {
                // 네이버 길찾기 코드
            },
            onMyMapSelectClick = {
                isMyMap = !isMyMap
            }
        )
    }
}

@Composable
fun PlaceDetailBottomBar(
    count: Int,
    onSpoonEatClick: () -> Unit,
    onSearchMapClick: () -> Unit,
    onMyMapSelectClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSpoonEat: Boolean = false,
    isMyMap: Boolean = false
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
        if (isSpoonEat) {
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
                    .noRippleClickable(onMyMapSelectClick),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = if (!isMyMap) ImageVector.vectorResource(id = R.drawable.ic_add_map_gray400_24) else ImageVector.vectorResource(id = R.drawable.ic_add_map_main400_24),
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
                onClick = onSpoonEatClick,
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

@Preview(showBackground = true)
@Composable
private fun PlaceDetailScreenPreview() {
    SpoonyAndroidTheme {
        PlaceDetailScreen({})
    }
}
