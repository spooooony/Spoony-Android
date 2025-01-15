package com.spoony.spoony.presentation.placeDetail.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme

@Composable
fun StoreInfo(
    menuItems: List<String>,
    locationItems: List<String>,
    modifier: Modifier = Modifier,
    isBlurred: Boolean = true
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .then(
                if (isBlurred) {
                    Modifier.blur(16.dp)
                } else {
                    Modifier
                }
            )
    ) {
        StoreInfoItem(
            title = "Menu",
            items = menuItems,
            isMenu = true
        )
        HorizontalDashedLine()
        StoreInfoItem(
            title = "Location",
            subTitle = "이키",
            items = locationItems,
            isMenu = false
        )
    }
}

@Composable
private fun HorizontalDashedLine(
    modifier: Modifier = Modifier,
    color: Color = SpoonyAndroidTheme.colors.gray200,
    strokeWidth: Float = 2f,
    dashLengths: FloatArray = floatArrayOf(30f, 30f)
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(1.dp)
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
    SpoonyAndroidTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            StoreInfo(
                modifier = Modifier
                    .padding(horizontal = 20.dp),
                isBlurred = true,
                menuItems = listOf(
                    "고등어봉초밥",
                    "크렘브륄레"
                ),
                locationItems = listOf(
                    "서울 서대문구 연희로11가길 39"
                )
            )
            StoreInfo(
                modifier = Modifier
                    .padding(horizontal = 20.dp),
                isBlurred = false,
                menuItems = listOf(
                    "고등어봉초밥",
                    "크렘브륄레"
                ),
                locationItems = listOf(
                    "서울 서대문구 연희로11가길 39"
                )
            )
        }
    }
}
