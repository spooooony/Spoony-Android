package com.spoony.spoony.core.designsystem.component.datepicker

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.theme.gray200
import com.spoony.spoony.core.util.extension.fadingEdge

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SpoonyBasicPicker(
    items: List<String>,
    initialSelectedIndex: Int,
    onSelectedItemChanged: (String) -> Unit,
    textAlign: TextAlign = TextAlign.Center,
    modifier: Modifier = Modifier,
    visibleItemsCount: Int = 5
) {
    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = (initialSelectedIndex - visibleItemsCount / 2).coerceAtLeast(0)
    )
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    val itemHeightPixels = remember { mutableIntStateOf(0) }
    val itemHeightDp = with(LocalDensity.current) { itemHeightPixels.intValue.toDp() }

    val visibleItemsMiddle = visibleItemsCount / 2

    val paddedItems = remember(items) {
        val paddingCount = visibleItemsCount / 2
        List(paddingCount) { "" } + items + List(paddingCount) { "" }
    }

    val selectedIndex by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex + visibleItemsMiddle
        }
    }

    LaunchedEffect(selectedIndex) {
        val actualIndex = selectedIndex - visibleItemsMiddle
        if (actualIndex in items.indices) {
            onSelectedItemChanged(items[actualIndex])
        }
    }

    LaunchedEffect(Unit) {
        if (items.isNotEmpty() && initialSelectedIndex >= 0) {
            listState.scrollToItem(
                index = (initialSelectedIndex - visibleItemsCount / 2 + visibleItemsMiddle).coerceAtLeast(0),
                scrollOffset = 0
            )
        }
    }

    val fadingEdgeGradient = remember {
        Brush.verticalGradient(
            0f to gray200,
            0.5f to Color.Black,
            1f to gray200
        )
    }

    Box(modifier = modifier) {
        LazyColumn(
            state = listState,
            flingBehavior = flingBehavior,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .height(itemHeightDp * visibleItemsCount)
                .fadingEdge(fadingEdgeGradient)
        ) {
            items(paddedItems) { item ->
                val isSelected = paddedItems.indexOf(item) == selectedIndex

                Text(
                    text = item,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = textAlign,
                    style = SpoonyAndroidTheme.typography.body2m,
                    color = if (isSelected) {
                        SpoonyAndroidTheme.colors.gray900
                    } else {
                        SpoonyAndroidTheme.colors.gray300
                    },
                    modifier = Modifier
                        .onSizeChanged { size -> itemHeightPixels.intValue = size.height }
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                )
            }
        }

        HorizontalDivider(
            color = SpoonyAndroidTheme.colors.gray500,
            modifier = Modifier.offset(y = itemHeightDp * visibleItemsMiddle)
        )

        HorizontalDivider(
            color = SpoonyAndroidTheme.colors.gray500,
            modifier = Modifier.offset(y = itemHeightDp * (visibleItemsMiddle + 1))
        )
    }
}
