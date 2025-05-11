package com.spoony.spoony.presentation.placeDetail.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.immutableListOf

@Composable
fun StoreInfo(
    menuList: ImmutableList<String>,
    locationSubTitle: String,
    location: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
    ) {
        StoreInfoItem(
            title = stringResource(id = R.string.place_detail_store_info_menu_title),
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
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                menuList.forEach { menuItem ->
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
        }
        HorizontalDashedLine()
        StoreInfoItem(
            title = stringResource(id = R.string.place_detail_store_info_location_title),
            padding = PaddingValues(
                vertical = 21.dp,
                horizontal = 16.dp
            ),
            shape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp,
                bottomStart = 8.dp,
                bottomEnd = 8.dp
            )
        ) {
            Text(
                locationSubTitle,
                style = SpoonyAndroidTheme.typography.title3sb
            )
            Spacer(modifier = Modifier.height(11.dp))
            PlaceDetailIconText(
                icon = ImageVector.vectorResource(R.drawable.ic_pin_24),
                iconSize = 20.dp,
                text = location,
                textStyle = SpoonyAndroidTheme.typography.body2m,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun HorizontalDashedLine(
    modifier: Modifier = Modifier,
    color: Color = SpoonyAndroidTheme.colors.gray200,
    strokeWidth: Float = 2f,
    dashLengths: FloatArray = floatArrayOf(30f, 30f)
) {
    val density = LocalDensity.current
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(with(density) { 1 / density.density }.dp)
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val pathEffect = PathEffect.dashPathEffect(
                intervals = dashLengths,
                phase = 0f
            )
            val horizontalPadding = 25.dp.toPx()
            val yPos = size.height / 2
            drawLine(
                color = color,
                start = Offset(x = horizontalPadding, y = yPos),
                end = Offset(x = size.width - horizontalPadding, y = yPos),
                strokeWidth = strokeWidth,
                pathEffect = pathEffect
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StoreInfoPreview() {
    val menuList = immutableListOf(
        "고등어봉초밥",
        "크렘브륄레",
        "사케"
    )
    SpoonyAndroidTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            StoreInfo(
                modifier = Modifier
                    .padding(horizontal = 20.dp),
                menuList = menuList,
                locationSubTitle = "어키",
                location = "서울 마포구 연희로11가길 39"
            )
        }
    }
}
