package com.spoony.spoony.presentation.placeDetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import kotlinx.collections.immutable.immutableListOf

@Composable
fun StoreInfoItem(
    title: String,
    shape: Shape,
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                shape = shape,
                color = SpoonyAndroidTheme.colors.gray0
            )
            .padding(padding)
    ) {
        Text(
            title,
            style = SpoonyAndroidTheme.typography.body1b
        )
        Spacer(modifier = Modifier.height(12.dp))
        content()
    }
}

@Preview
@Composable
private fun StoreInfoItemMenuPreview() {
    val menuItems = immutableListOf(
        "고등어봉초밥",
        "크렘브륄레",
        "사케"
    )
    SpoonyAndroidTheme {
        StoreInfoItem(
            title = "Menu",
            content = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    menuItems.forEach { menuItem ->
                        key(menuItem) {
                            PlaceDetailIconText(
                                icon = ImageVector.vectorResource(R.drawable.ic_spoon_24),
                                iconSize = 20.dp,
                                text = menuItem,
                                textStyle = SpoonyAndroidTheme.typography.body2m,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            },
            padding = PaddingValues(
                top = 20.dp,
                bottom = 28.dp,
                start = 16.dp,
                end = 16.dp
            ),
            shape = RoundedCornerShape(
                topStart = 8.dp,
                topEnd = 8.dp,
                bottomStart = 20.dp,
                bottomEnd = 20.dp
            )
        )
    }
}

@Preview
@Composable
private fun StoreInfoItemLocationPreview() {
    SpoonyAndroidTheme {
        StoreInfoItem(
            title = "Location",
            content = {
                Text(
                    "어키",
                    style = SpoonyAndroidTheme.typography.title2sb
                )
                Spacer(modifier = Modifier.height(11.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    PlaceDetailIconText(
                        icon = ImageVector.vectorResource(R.drawable.ic_pin_24),
                        iconSize = 20.dp,
                        text = "서울 마포구 연희로11가길 39",
                        textStyle = SpoonyAndroidTheme.typography.body2m,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            padding = PaddingValues(
                vertical = 22.dp,
                horizontal = 16.dp
            ),
            shape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp,
                bottomStart = 8.dp,
                bottomEnd = 8.dp
            )
        )
    }
}
