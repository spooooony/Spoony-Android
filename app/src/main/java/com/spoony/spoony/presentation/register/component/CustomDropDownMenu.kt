package com.spoony.spoony.presentation.register.component

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme

@Composable
fun CustomDropDownMenu(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    isVisible: Boolean = false,
    horizontalPadding: Dp = 20.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(300),
        label = ""
    )

    val scale by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = ""
    )

    if (isVisible) {
        Box(
            modifier = modifier
        ) {
            Popup(onDismissRequest = onDismissRequest) {
                Column(
                    modifier = Modifier
                        .background(Color.Unspecified)
                        .graphicsLayer {
                            scaleY = scale
                            this.alpha = alpha
                            transformOrigin = TransformOrigin(0.5f, 0f)
                        }
                        .padding(horizontal = horizontalPadding)
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, SpoonyAndroidTheme.colors.gray100, RoundedCornerShape(8.dp)),
                    content = content
                )
            }
        }
    }
}

@Composable
fun DropdownMenuItem(
    placeName: String,
    placeRoadAddress: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    showDivider: Boolean = true
) {
    Column(
        modifier = modifier
            .background(SpoonyAndroidTheme.colors.white)
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .clickable(onClick = onClick)
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp)

        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_pin_24),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = placeName,
                    style = SpoonyAndroidTheme.typography.body2b,
                    color = SpoonyAndroidTheme.colors.black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = placeRoadAddress,
                    style = SpoonyAndroidTheme.typography.caption1m,
                    color = SpoonyAndroidTheme.colors.gray500,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        if (showDivider) {
            HorizontalDivider(
                color = SpoonyAndroidTheme.colors.gray100,
                thickness = 1.dp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    val density = LocalDensity.current
    var isDropdownVisible by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableIntStateOf(0) }

    SpoonyAndroidTheme {
        Box(modifier = Modifier.background(Color.White)) {
            TextField(
                value = "Press Enter",
                onValueChange = { },
                placeholder = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned {
                        textFieldSize = with(density) {
                            it.size.height.toDp().value.toInt()
                        }
                    }
                    .clickable { isDropdownVisible = true }
            )

            CustomDropDownMenu(
                isVisible = isDropdownVisible,
                onDismissRequest = { isDropdownVisible = false },
                modifier = Modifier
                    .offset { IntOffset(0, (textFieldSize + 4).dp.roundToPx()) }
                    .background(Color.White)
            ) {
                val places = listOf(
                    "파오리 본점" to "서울 중구 동호로5길 2-11층",
                    "파오리 강남점" to "서울 강남구 테헤란로 124 4층",
                    "파오리 홍대점" to "서울 마포구 와우산로 29-1",
                    "파오리 신촌점" to "서울 서대문구 연세로 8 지하1층",
                    "파오리 건대점" to "서울 광진구 아차산로 177 2층"
                )

                places.take(5).forEachIndexed { index, (name, address) ->
                    DropdownMenuItem(
                        placeName = name,
                        placeRoadAddress = address,
                        onClick = { isDropdownVisible = false },
                        showDivider = index < places.size - 1
                    )
                }
            }
        }
    }
}
