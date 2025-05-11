package com.spoony.spoony.presentation.explore.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.util.extension.noRippleClickable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ExploreTabRow(
    modifier: Modifier = Modifier,
    selectedTabIndex: MutableState<Int>,
    tabList: ImmutableList<String> = persistentListOf("전체", "팔로잉")
) {
    var startX by remember { mutableStateOf(0.dp) }
    val tabWidths = remember { mutableListOf<Float>() }
    var rowStartX by remember { mutableFloatStateOf(0f) }
    val density = LocalDensity.current

    Column {
        Row(
            modifier = modifier
                .onGloballyPositioned { coordinates ->
                    rowStartX = coordinates.positionInRoot().x
                },
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            tabList.forEachIndexed { index, tab ->
                ExploreTab(
                    label = tab,
                    selected = selectedTabIndex.value == index,
                    onClick = {
                        selectedTabIndex.value = index
                    },
                    onStartXChange = { x ->
                        if (index == selectedTabIndex.value) {
                            startX = with(density) { (x - rowStartX + 2).toDp() }
                        }
                    },
                    onWidthChange = { width ->
                        if (tabWidths.size <= index) {
                            tabWidths.add(width)
                        } else {
                            tabWidths[index] = width
                        }
                    }
                )
            }
        }

        val animatedStartX by animateDpAsState(
            targetValue = startX,
            animationSpec = tween(
                durationMillis = 300,
                easing = FastOutSlowInEasing
            ),
            label = "animateStartX"
        )

        val indicatorWidth = if (tabWidths.isNotEmpty()) {
            with(density) { tabWidths[selectedTabIndex.value].toDp() }
        } else {
            0.dp
        }

        val animatedIndicatorWidth by animateDpAsState(
            targetValue = indicatorWidth,
            animationSpec = tween(
                durationMillis = 300,
                easing = FastOutSlowInEasing
            ),
            label = "indicatorWidth"
        )

        Spacer(
            modifier = Modifier
                .padding(top = 5.dp)
                .offset {
                    IntOffset(
                        x = animatedStartX.roundToPx(),
                        y = 0
                    )
                }
                .width(animatedIndicatorWidth)
                .height(Dp.Hairline.plus(2.dp))
                .background(SpoonyAndroidTheme.colors.main400)
        )
    }
}

@Composable
private fun ExploreTab(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    onStartXChange: (Float) -> Unit,
    onWidthChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    var startX by remember { mutableFloatStateOf(0f) }

    Surface(
        modifier = modifier
            .noRippleClickable(onClick = onClick),
        color = Color.Transparent
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.onGloballyPositioned { coordinates ->
                    val globalStartX = coordinates.positionInRoot().x
                    if (selected) {
                        startX = globalStartX
                        onStartXChange(startX)
                    }
                    onWidthChange(coordinates.size.width.toFloat())
                },
                text = label,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = SpoonyAndroidTheme.typography.title3sb,
                color = if (selected) SpoonyAndroidTheme.colors.main400 else SpoonyAndroidTheme.colors.gray300
            )
        }
    }
}
