package com.spoony.spoony.presentation.placeDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.component.tag.IconTag
import com.spoony.spoony.core.designsystem.component.topappbar.TagTopAppBar
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.presentation.placeDetail.component.IconDropdownMenu
import com.spoony.spoony.presentation.placeDetail.component.StoreInfo
import com.spoony.spoony.presentation.placeDetail.component.UserProfileInfo
import kotlinx.collections.immutable.immutableListOf

@Composable
fun PlaceDetailScreen() {
    val menuItems = immutableListOf(
        "고등어봉초밥",
        "크렘브륄레",
        "사케"
    )
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TagTopAppBar(
            count = 99,
            showBackButton = true
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
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
                    imageUrl = "https://gratisography.com/wp-content/uploads/2024/10/gratisography-cool-cat-800x525.jpg",
                    name = "클레오가트라",
                    location = "마포구 수저",
                    modifier = Modifier.weight(1f)
                )
                IconDropdownMenu(
                    menuItems = immutableListOf("신고하기"),
                    onMenuItemClick = { selectedItem ->
                        // selectedItem
                    }
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            IconTag(
                text = "chip",
                backgroundColorHex = "EEE3FD",
                textColorHex = "AD75F9",
                iconUrl = "https://github.com/user-attachments/assets/18469f99-9570-40d3-a3c0-c6a6bb1c426f",
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "인생 이자카야. 고등어 초밥 안주가 그냥 미쳤어요.",
                style = SpoonyAndroidTheme.typography.title1,
                color = SpoonyAndroidTheme.colors.black,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "2025년 1월 2일",
                style = SpoonyAndroidTheme.typography.caption1m,
                color = SpoonyAndroidTheme.colors.gray400,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                "이자카야인데 친구랑 가서 안주만 5개 넘게 시킴.. 명성이 자자한 고등어봉 초밥은 꼭 시키세요! 입에 넣자마자 사르르 녹아 없어짐. 그리고 밤 후식 진짜 맛도리니까 밤 디저트 좋아하는 사람이면 꼭 먹어보기! ",
                style = SpoonyAndroidTheme.typography.body2m,
                color = SpoonyAndroidTheme.colors.gray900,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            StoreInfo(
                modifier = Modifier
                    .padding(horizontal = 20.dp),
                isBlurred = false,
                menuItems = menuItems,
                locationSubTitle = "어키",
                location = "서울 마포구 연희로11가길 39"
            )
            Spacer(modifier = Modifier.height(103.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PlaceDetailScreenPreview() {
    SpoonyAndroidTheme {
        PlaceDetailScreen()
    }
}
