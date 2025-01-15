package com.spoony.spoony.presentation.placeDetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme

@Composable
fun StoreInfoItem(
    title: String,
    subTitle: String? = null,
    items: List<String>,
    isMenu: Boolean = true,
    modifier: Modifier = Modifier
) {
    val (shape, paddingValues) = remember(isMenu) {
        when (isMenu) {
            true -> RoundedCornerShape(
                topStart = 8.dp,
                topEnd = 8.dp,
                bottomStart = 20.dp,
                bottomEnd = 20.dp
            ) to PaddingValues(
                top = 20.dp,
                bottom = 28.dp,
                start = 16.dp,
                end = 16.dp
            )
            false -> RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp,
                bottomStart = 8.dp,
                bottomEnd = 8.dp
            ) to PaddingValues(
                vertical = 22.dp,
                horizontal = 16.dp
            )
        }
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                shape = shape,
                color = SpoonyAndroidTheme.colors.gray0
            )
            .padding(paddingValues)
    ) {
        Text(
            title,
            style = SpoonyAndroidTheme.typography.body1b
        )
        Spacer(modifier = Modifier.height(12.dp))
        if (subTitle != null) {
            Text(
                subTitle,
                style = SpoonyAndroidTheme.typography.title2sb
            )
            Spacer(modifier = Modifier.height(11.dp))
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items.forEach { item ->
                IconText(
                    icon = if (isMenu) R.drawable.ic_spoon_24 else R.drawable.ic_pin_24,
                    iconSize = 20,
                    text = item,
                    textStyle = SpoonyAndroidTheme.typography.body2m,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun IconText(
    icon: Int,
    iconSize: Int,
    text: String,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
    textColor: Color = SpoonyAndroidTheme.colors.black,
    iconTint: Color = SpoonyAndroidTheme.colors.gray600
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(icon),
            contentDescription = null,
            modifier = Modifier.size(iconSize.dp),
            tint = iconTint
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            color = textColor,
            style = textStyle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
private fun StoreInfoItemMenuPreview() {
    SpoonyAndroidTheme {
        StoreInfoItem(
            title = "Menu",
            items = listOf(
                "고등어봉초밥고등어봉초밥고등어봉초밥고등어봉초밥고등어봉초밥고등어봉초밥고등어봉초밥고등어봉초밥",
                "크렘브륄레"
            ),
            isMenu = true
        )
    }
}

@Preview
@Composable
private fun StoreInfoItemLocationPreview() {
    SpoonyAndroidTheme {
        StoreInfoItem(
            title = "Location",
            subTitle = "이키",
            items = listOf(
                "서울 서대문구 연희로11가길 39"
            ),
            isMenu = false
        )
    }
}
