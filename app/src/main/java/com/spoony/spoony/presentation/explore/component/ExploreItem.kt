package com.spoony.spoony.presentation.explore.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.tag.IconTag
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.util.extension.hexToColor
import com.spoony.spoony.presentation.explore.type.ExploreDropdownOption
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ExploreItem2 (
    username: String,
    placeSpoon: String,
    review: String,
    addMapCount: Int,
    date: String,
    iconUrl: String,
    tagText: String,
    textColor: Color,
    backgroundColor: Color,
    menuItems: List<ExploreDropdownOption>,
    onMenuItemClick: (ExploreDropdownOption) -> Unit,
    modifier: Modifier = Modifier
) {
    val addMapTextColor = if (addMapCount > 0) SpoonyAndroidTheme.colors.main400 else SpoonyAndroidTheme.colors.gray300
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(SpoonyAndroidTheme.colors.gray0)
            .padding(
                horizontal = 10.dp,
                vertical = 18.dp,
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconTag(
                text = tagText,
                iconUrl = iconUrl,
                textColor = textColor,
                backgroundColor = backgroundColor
            )
            Spacer(modifier = Modifier.weight(1f))
            IconDropdownMenu(
                menuItems,
                onMenuItemClick
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = username,
                style = SpoonyAndroidTheme.typography.body2b,
                color = SpoonyAndroidTheme.colors.black,
                modifier = Modifier
                    .padding(start = 5.dp, end = 4.dp)
            )

            Text(
                text = "서울 $placeSpoon 스푼",
                style = SpoonyAndroidTheme.typography.caption2m,
                color = SpoonyAndroidTheme.colors.gray500
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = review,
            style = SpoonyAndroidTheme.typography.caption1m,
            color = SpoonyAndroidTheme.colors.black,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(SpoonyAndroidTheme.colors.white)
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ){
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_add_map_main400_24),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = addMapCount.toString() + "명이 지도에 저장했어요!",
                    style = SpoonyAndroidTheme.typography.caption2b,
                    color = SpoonyAndroidTheme.colors.main400
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = date,
                style = SpoonyAndroidTheme.typography.caption2m,
                color = SpoonyAndroidTheme.colors.gray400
            )
        }
    }
}

@Preview
@Composable
fun PreviewExploreItem2() {
    val menuItems = persistentListOf(ExploreDropdownOption.REPORT)
    SpoonyAndroidTheme {
        ExploreItem2(
            username = "gambasgirl",
            placeSpoon = "성북구",
            review = "이자카야인데 친구랑 가서 안주만 5개 넘게 시킴.. 명성이 자자한 고등어봉 초밥은 꼭 시키세요! 입에 넣자마자 사르르 녹아 ...이자카야인데 친구랑 가서 안주만 5개 넘게 시킴.. 명성이 자자한 고등어봉 초밥은 꼭 시키세요! 입에 넣자마자 사르르 녹아 ...",
            addMapCount = 0,
            tagText = "카페",
            date = "약 5시간 전",
            backgroundColor = Color.hexToColor("FFCEC6"),
            textColor = Color.hexToColor("FF5235"),
            iconUrl = "https://github.com/user-attachments/assets/67b8de6f-d4e8-4123-bd7d-93623e41ea8c",
            menuItems = menuItems,
            onMenuItemClick = { option ->
                when (option) {
                    ExploreDropdownOption.REPORT -> {
                        // Handle report option
                    }
                }
            },
            modifier = Modifier
        )
    }
}